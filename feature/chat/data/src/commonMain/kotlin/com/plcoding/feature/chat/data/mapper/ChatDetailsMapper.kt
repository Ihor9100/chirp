package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails

class ChatDetailsMapper(
  private val chatEntityMapper: ChatEntityMapper,
  private val chatMessageAndMemberMapper: ChatMessageAndMemberMapper,
) : Mapper<ChatAndMembersAndMessagesRelation, ChatDetails> {

  override fun map(from: ChatAndMembersAndMessagesRelation): ChatDetails {
    return with(from) {
      ChatDetails(
        chat = getChat(this),
        chatMessagesAndMembers = chatMessageAndMemberMapper
          .mapList(from.chatMessageAndMemberRelations),
      )
    }
  }

  private fun getChat(from: ChatAndMembersAndMessagesRelation): Chat {
    val sortedMessagesAndMembers = from
      .chatMessageAndMemberRelations
      .sortedByDescending { it.chatMessageEntity.timestamp }

    val params = ChatEntityMapper.Params(
      chatMemberEntities = from.chatMemberEntities,
      chatLastMessage = sortedMessagesAndMembers.firstOrNull()?.chatMessageEntity,
    )

    return chatEntityMapper.reverse(from.chatEntity, params)
  }
}
