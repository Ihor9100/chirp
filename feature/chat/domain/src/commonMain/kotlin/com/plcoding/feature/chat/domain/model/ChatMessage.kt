package com.plcoding.feature.chat.domain.model

data class ChatMessage(
  val id: String,
  val chatId: String,
  val senderId: String,
  val content: String,
  val createdAt: String,
) {

  companion object {
    val mock
      get() = ChatMessage(
        id = "1",
        chatId = "1",
        senderId = "1",
        content = "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest",
        createdAt = "1",
      )
  }
}
