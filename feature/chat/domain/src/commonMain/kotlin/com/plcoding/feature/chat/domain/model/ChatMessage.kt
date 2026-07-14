package com.plcoding.feature.chat.domain.model

import kotlin.time.Clock
import kotlin.time.Instant

data class ChatMessage(
  val id: String,
  val chatId: String,
  val senderId: String,
  val content: String,
  val createdAt: Instant,
  val deliveryStatus: ChatMessageDeliveryStatus,
) {

  companion object {
    val mock
      get() = List(10) {
        ChatMessage(
          id = "$it",
          chatId = "${it + 1}",
          senderId = "${it + 2}",
          content = "Test TestTest TestTest TestTestTe stTestTest TestTestTestTest TestTes tTestTestTest",
          createdAt = Clock.System.now(),
          deliveryStatus = ChatMessageDeliveryStatus.SENT
        )
      }
  }
}
