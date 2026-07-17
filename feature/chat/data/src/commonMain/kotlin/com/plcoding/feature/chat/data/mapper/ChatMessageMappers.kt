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

fun ChatLastMessageView.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  createdAt = Instant.fromEpochMilliseconds(timestamp),
  deliveryStatus = ChatMessageDeliveryStatus.valueOf(status),
)

fun WebSocketPayloadDto.NewMessageDto.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  createdAt = Instant.parse(createdAt),
  deliveryStatus = ChatMessageDeliveryStatus.SENT,
)

fun WebSocketPayloadDto.NewMessageDto.toEntity(): ChatMessageEntity = ChatMessageEntity(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  timestamp = Instant.parse(createdAt).toEpochMilliseconds(),
  status = ChatMessageDeliveryStatus.SENT.name,
)

fun ChatMessage.toDto(): NewMessageDto = NewMessageDto(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  createdAt = createdAt.toString(),
)

fun ChatMessage.toEntity(
  senderId: String,
): ChatMessageEntity {
  return ChatMessageEntity(
    id = id,
    chatId = chatId,
    senderId = senderId,
    content = content,
    timestamp = createdAt.toEpochMilliseconds(),
    status = deliveryStatus.name,
  )
}


fun ChatMessage.toDto(
  senderId: String,
): WebSocketPayloadDto.NewMessageDto {
  return WebSocketPayloadDto.NewMessageDto(
    id=id,
      chatId=chatId,
      senderId=senderId,
      content=content,
      createdAt=createdAt,
    messageType = ,
  )
}
