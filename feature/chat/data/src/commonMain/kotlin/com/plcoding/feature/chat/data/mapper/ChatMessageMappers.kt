package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.data.model.ChatMessageDto
import com.plcoding.feature.chat.data.model.WebSocketPayloadDto
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.view.ChatLastMessageView
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
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

fun ChatMessageEntity.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
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

fun ChatLastMessageView.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  createdAt = Instant.fromEpochMilliseconds(timestamp),
  deliveryStatus = ChatMessageDeliveryStatus.valueOf(status),
)

fun WebSocketPayloadDto.IncomingMessageDto.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
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

fun ChatMessage.toIncomingMessageDto(): WebSocketPayloadDto.IncomingMessageDto {
  return WebSocketPayloadDto.IncomingMessageDto(
    id = id,
    chatId = chatId,
    senderId = senderId,
    content = content,
    createdAt = createdAt.toString(),
  )
}

fun ChatMessage.toOutgoingMessageDto(): WebSocketPayloadDto.OutgoingMessageDto {
  return WebSocketPayloadDto.OutgoingMessageDto(
    messageId = id,
    chatId = chatId,
    content = content,
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
