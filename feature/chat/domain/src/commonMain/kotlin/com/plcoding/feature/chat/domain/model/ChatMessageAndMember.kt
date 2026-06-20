package com.plcoding.feature.chat.domain.model

import kotlin.time.Clock
import kotlin.time.Instant

data class ChatMessageAndMember(
  val chatMessage: ChatMessage,
  val chatMember: ChatMember,
  val deliveryStatus: ChatMessageDeliveryStatus,
)
