package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.data.model.ChatMessageDto
import com.plcoding.feature.chat.data.model.ChatMessageAttachmentDto
import com.plcoding.feature.chat.data.model.WebSocketPayloadDto
import com.plcoding.feature.chat.database.entity.ChatMessageAttachmentEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.view.ChatLastMessageView
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageAttachment
import com.plcoding.feature.chat.domain.model.ChatMessageAttachmentType
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  attachments = attachments.map { it.toDomain(id) },
  createdAt = Instant.parse(createdAt),
  deliveryStatus = ChatMessageDeliveryStatus.SENT,
)

fun ChatMessageDto.toEntity(): ChatMessageEntity = ChatMessageEntity(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  timestamp = Instant.parse(createdAt).toEpochMilliseconds(),
  status = ChatMessageDeliveryStatus.SENT.name,
)

fun ChatMessageDto.toAttachmentEntities(): List<ChatMessageAttachmentEntity> {
  return attachments.map { it.toEntity(id) }
}

fun ChatMessageEntity.toDomain(
  attachments: List<ChatMessageAttachmentEntity> = emptyList(),
): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  attachments = attachments.map { it.toDomain() },
  createdAt = Instant.fromEpochMilliseconds(timestamp),
  deliveryStatus = ChatMessageDeliveryStatus.valueOf(status),
)


fun ChatMessageEntity.toDto(): WebSocketPayloadDto.OutgoingMessageDto {
  return WebSocketPayloadDto.OutgoingMessageDto(
    messageId = id,
    chatId = chatId,
    content = content,
  )
}

fun ChatLastMessageView.toDomain(
  attachments: List<ChatMessageAttachmentEntity> = emptyList(),
): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  attachments = attachments.map { it.toDomain() },
  createdAt = Instant.fromEpochMilliseconds(timestamp),
  deliveryStatus = ChatMessageDeliveryStatus.valueOf(status),
)

fun WebSocketPayloadDto.IncomingMessageDto.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  attachments = attachments.map { it.toDomain(id) },
  createdAt = Instant.parse(createdAt),
  deliveryStatus = ChatMessageDeliveryStatus.SENT,
)

fun WebSocketPayloadDto.IncomingMessageDto.toEntity(): ChatMessageEntity = ChatMessageEntity(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  timestamp = Instant.parse(createdAt).toEpochMilliseconds(),
  status = ChatMessageDeliveryStatus.SENT.name,
)

fun WebSocketPayloadDto.IncomingMessageDto.toAttachmentEntities(): List<ChatMessageAttachmentEntity> {
  return attachments.map { it.toEntity(id) }
}

fun ChatMessage.toIncomingMessageDto(): WebSocketPayloadDto.IncomingMessageDto {
  return WebSocketPayloadDto.IncomingMessageDto(
    id = id,
    chatId = chatId,
    senderId = senderId,
    content = content,
    attachments = attachments.map { it.toDto() },
    createdAt = createdAt.toString(),
  )
}

fun ChatMessage.toOutgoingMessageDto(): WebSocketPayloadDto.OutgoingMessageDto {
  return WebSocketPayloadDto.OutgoingMessageDto(
    messageId = id,
    chatId = chatId,
    content = content,
    attachments = attachments.map { it.toDto() },
  )
}

fun ChatMessage.toEntity(): ChatMessageEntity {
  return ChatMessageEntity(
    id = id,
    chatId = chatId,
    senderId = senderId,
    content = content,
    timestamp = createdAt.toEpochMilliseconds(),
    status = deliveryStatus.name,
  )
}

fun ChatMessage.toAttachmentEntities(): List<ChatMessageAttachmentEntity> {
  return attachments.map { it.toEntity() }
}

fun ChatMessageAttachmentDto.toDomain(messageId: String): ChatMessageAttachment {
  return ChatMessageAttachment(
    id = id,
    messageId = this.messageId ?: messageId,
    url = url,
    mimeType = mimeType,
    sizeBytes = sizeBytes,
    type = ChatMessageAttachmentType.valueOf(type),
  )
}

fun ChatMessageAttachmentDto.toEntity(messageId: String): ChatMessageAttachmentEntity {
  return ChatMessageAttachmentEntity(
    id = id,
    messageId = this.messageId ?: messageId,
    url = url,
    mimeType = mimeType,
    sizeBytes = sizeBytes,
    type = type,
  )
}

fun ChatMessageAttachmentEntity.toDomain(): ChatMessageAttachment {
  return ChatMessageAttachment(
    id = id,
    messageId = messageId,
    url = url,
    mimeType = mimeType,
    sizeBytes = sizeBytes,
    type = ChatMessageAttachmentType.valueOf(type),
  )
}

fun ChatMessageAttachment.toDto(): ChatMessageAttachmentDto {
  return ChatMessageAttachmentDto(
    id = id,
    messageId = messageId,
    url = url,
    mimeType = mimeType,
    sizeBytes = sizeBytes,
    type = type.name,
  )
}

fun ChatMessageAttachment.toEntity(): ChatMessageAttachmentEntity {
  return ChatMessageAttachmentEntity(
    id = id,
    messageId = messageId,
    url = url,
    mimeType = mimeType,
    sizeBytes = sizeBytes,
    type = type.name,
  )
}
