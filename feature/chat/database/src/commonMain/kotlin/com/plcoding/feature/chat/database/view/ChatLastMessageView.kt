package com.plcoding.feature.chat.database.view

import androidx.room.DatabaseView

@DatabaseView(
  viewName = "chat_last_messages",
  value = """
    SELECT m1.*
    FROM chat_messages m1
    JOIN(
      SELECT chatId, MAX(timestamp) AS max_timestamp
      FROM chat_messages
      GROUP BY chatId
    ) m2 ON m1.chatId = m2.chatId AND m1.timestamp = max_timestamp
  """
)
data class ChatLastMessageView(
  val id: String,
  val chatId: String,
  val senderId: String,
  val content: String,
  val timestamp: Long,
  val status: String,
)
