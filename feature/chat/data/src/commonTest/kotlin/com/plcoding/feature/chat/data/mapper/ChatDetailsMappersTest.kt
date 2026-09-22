package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageAttachmentEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.database.relation.ChatMessageAndMemberRelation
import com.plcoding.feature.chat.domain.model.ChatMessageAttachmentType
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class ChatDetailsMappersTest {

  @Test
  fun `maps last message attachments from chat details relation`() {
    val message = ChatMessageEntity(
      id = "message-id",
      chatId = "chat-id",
      senderId = "sender-id",
      content = null,
      timestamp = 100,
      status = ChatMessageDeliveryStatus.SENT.name,
    )
    val attachment = ChatMessageAttachmentEntity(
      id = "attachment-id",
      messageId = message.id,
      url = "https://example.com/image.jpg",
      mimeType = "image/jpeg",
      sizeBytes = 100,
      type = ChatMessageAttachmentType.IMAGE.name,
    )

    val chatDetails = ChatAndMembersAndMessagesRelation(
      chatEntity = ChatEntity(
        id = "chat-id",
        lastActivityAt = 100,
      ),
      chatMemberEntities = listOf(
        ChatMemberEntity(
          id = "sender-id",
          name = "Sender",
          avatarUrl = null,
        )
      ),
      chatMessageAndMemberRelations = listOf(
        ChatMessageAndMemberRelation(
          chatMessageEntity = message,
          chatMemberEntity = ChatMemberEntity(
            id = "sender-id",
            name = "Sender",
            avatarUrl = null,
          ),
          attachmentEntities = listOf(attachment),
        )
      ),
    ).toDomain()

    assertEquals(1, chatDetails.chat.lastMessage?.attachments?.size)
    assertEquals(attachment.id, chatDetails.chat.lastMessage?.attachments?.first()?.id)
  }
}
