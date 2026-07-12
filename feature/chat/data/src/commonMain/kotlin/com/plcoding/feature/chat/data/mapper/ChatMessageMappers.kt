package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.data.model.ChatMessageAm
import com.plcoding.feature.chat.data.model.WebSocketPayloadAm
import com.plcoding.feature.chat.data.model.WebSocketPayloadAm.NewMessageAm
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.view.ChatLastMessageView
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.time.Instant

fun ChatMessageAm.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  createdAt = Instant.parse(createdAt),
  deliveryStatus = ChatMessageDeliveryStatus.SENT,
)

fun ChatMessageAm.toEntity(): ChatMessageEntity = ChatMessageEntity(
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

fun WebSocketPayloadAm.NewMessageAm.toDomain(): ChatMessage = ChatMessage(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  createdAt = Instant.parse(createdAt),
  deliveryStatus = ChatMessageDeliveryStatus.SENT,
)

fun WebSocketPayloadAm.NewMessageAm.toEntity(): ChatMessageEntity = ChatMessageEntity(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  timestamp = Instant.parse(createdAt).toEpochMilliseconds(),
  status = ChatMessageDeliveryStatus.SENT.name,
)

fun ChatMessage.toAm(): NewMessageAm = NewMessageAm(
  id = id,
  chatId = chatId,
  senderId = senderId,
  content = content,
  createdAt = createdAt.toString(),
)
