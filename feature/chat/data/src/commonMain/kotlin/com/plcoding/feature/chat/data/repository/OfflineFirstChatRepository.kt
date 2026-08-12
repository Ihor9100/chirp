package com.plcoding.feature.chat.data.repository

import com.plcoding.core.domain.model.User
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.asEmpty
import com.plcoding.core.domain.result.flatMap
import com.plcoding.core.domain.result.map
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.feature.chat.data.datasource.local.ChatsLocalDataSource
import com.plcoding.feature.chat.data.datasource.remote.ChatsRemoteDataSource
import com.plcoding.feature.chat.data.mapper.toDomain
import com.plcoding.feature.chat.data.mapper.toEntities
import com.plcoding.feature.chat.data.mapper.toEntity
import com.plcoding.feature.chat.data.model.ChatDto
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageAndMember
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class OfflineFirstChatRepository(
  private val localDataSource: ChatsLocalDataSource,
  private val remoteDataSource: ChatsRemoteDataSource,
  private val preferencesRepository: PreferencesRepository,
) : ChatRepository {

  override fun observeChats(): Flow<List<Chat>> {
    return localDataSource
      .observeChatAndMembers()
      .map { entities -> entities.map { it.toDomain() } }
  }

  override fun observeChatDetails(chatId: String): Flow<ChatDetails?> {
    return localDataSource
      .observeChatAndMembersAndMessages(chatId)
      .map { it?.toDomain() }
  }

  override fun observeChatMembers(chatId: String): Flow<List<ChatMember>> {
    return localDataSource
      .observeChatMembers(chatId)
      .map { entities -> entities.map { it.toDomain() } }
  }

  override fun observeChatMessages(chatId: String): Flow<List<ChatMessageAndMember>> {
    return localDataSource
      .observeChatMessages(chatId)
      .map { entities -> entities.map { it.toDomain() } }
  }

  override suspend fun deleteChats() {
    localDataSource.deleteChats()
  }

  override suspend fun searchChatMember(query: String): Result<ChatMember, DataError.Remote> {
    return remoteDataSource
      .searchChatMember(query)
      .map { it.toDomain() }
  }

  override suspend fun createChat(memberIds: List<String>): Empty<DataError> {
    return remoteDataSource
      .createChat(memberIds)
      .flatMap { upsertChatDetails(it) }
  }

  override suspend fun syncChat(chatId: String): Empty<DataError> {
    return remoteDataSource
      .getChat(chatId)
      .flatMap { upsertChatDetails(it) }
  }

  private suspend fun upsertChatDetails(chatDto: ChatDto): Empty<DataError> {
    return localDataSource.upsertChatDetails(
      chatDto.toEntity(),
      chatDto.participants.map { it.toEntity() },
      listOfNotNull(chatDto.lastMessage).map { it.toEntity() },
      chatDto.toEntities(),
    )
  }

  override suspend fun syncLocalUser(): Empty<DataError> {
    return remoteDataSource
      .getLocalUser()
      .onSuccess {
        preferencesRepository
          .observeAuthInfo().first()
          ?.let { authInfo ->
            updateAuthInfoUser {
              copy(
                id = it.userId,
                username = it.username,
                email = it.email ?: authInfo.user.email,
                profilePictureUrl = it.profilePictureUrl,
              )
            }
          }
      }
      .asEmpty()
  }

  override suspend fun syncChatMessages(
    chatId: String,
    before: String?,
  ): Result<List<ChatMessage>, DataError> {
    return remoteDataSource
      .getChatMessages(chatId, before)
      .flatMap { dtos ->
        val entities = dtos.map { it.toEntity() }

        // Remove all messages of particular chat if it is the first page
        if (before == null) {
          localDataSource.replaceChatMessages(chatId, entities)
        } else {
          localDataSource.upsertChatMessages(entities)
        }

        Result.Success(dtos.map { it.toDomain() })
      }
  }

  override suspend fun syncChats(): Empty<DataError> {
    return remoteDataSource
      .getChats()
      .flatMap { dtos ->
        localDataSource.replaceChatsDetails(
          chats = dtos.map { it.toEntity() },
          chatMembers = dtos.flatMap { it.participants }.map { it.toEntity() },
          chatMessages = dtos.mapNotNull { it.lastMessage }.map { it.toEntity() },
          chatsAndMembers = dtos.flatMap { it.toEntities() },
        )
      }
  }

  override suspend fun leaveChat(chatId: String): Empty<DataError> {
    return remoteDataSource
      .leaveChat(chatId)
      .flatMap { localDataSource.removeChatDetails(chatId) }
  }

  override suspend fun addChatMembers(
    chatId: String,
    memberIds: List<String>,
  ): Empty<DataError> {
    return remoteDataSource
      .addChatMembers(chatId, memberIds)
      .flatMap { upsertChatDetails(it) }
  }

  override suspend fun deleteChatMessage(messageId: String): Empty<DataError.Remote> {
    return remoteDataSource
      .deleteChatMessage(messageId)
      .onSuccess { localDataSource.deleteChatMessage(messageId) }
  }

  override suspend fun changePassword(
    oldPassword: String,
    newPassword: String
  ): Empty<DataError.Remote> {
    return remoteDataSource.changePassword(
      currentPassword = oldPassword,
      newPassword = newPassword,
    )
  }

  override suspend fun uploadProfileImage(
    byteArray: ByteArray,
    mimeType: String
  ): Empty<DataError> {
    val createUploadResult = remoteDataSource.createProfileImageUpload(mimeType)

    if (createUploadResult is Result.Failure) return createUploadResult

    val profileImageUploadDto = (createUploadResult as Result.Success).data

    val uploadResult = remoteDataSource.uploadProfileImage(
      publicUrl = profileImageUploadDto.uploadUrl,
      byteArray = byteArray,
      headers = profileImageUploadDto.headers,
    )

    if (uploadResult is Result.Failure) return uploadResult

    val publicUrl = profileImageUploadDto.publicUrl

    return remoteDataSource
      .confirmProfileImageUpload(publicUrl)
      .onSuccess { updateAuthInfoUser { copy(profilePictureUrl = publicUrl) } }
  }

  override suspend fun deleteProfileImage(): Empty<DataError.Remote> {
    return remoteDataSource
      .deleteProfileImage()
      .onSuccess { updateAuthInfoUser { copy(profilePictureUrl = null) } }
  }

  private suspend fun updateAuthInfoUser(transform: User.() -> User) {
    val authInfo = preferencesRepository.observeAuthInfo().first() ?: return
    val updatedUser = authInfo.copy(user = transform(authInfo.user))
    preferencesRepository.saveAuthInfo(updatedUser)
  }
}
