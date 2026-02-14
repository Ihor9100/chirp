package com.plcoding.feature.chat.domain.model

data class ChatMember(
  val userId: String,
  val username: String,
  val profilePictureUrl: String?
) {

  companion object {
    val mocks
      get() = List(5) { index ->
        ChatMember(
          userId = "$index",
          username = "User $index",
          profilePictureUrl = if (index % 2 == 0) {
            "https://upload.wikimedia.org/wikipedia/commons/3/3a/Cat03.jpg"
          } else {
            "https://upload.wikimedia.org/wikipedia/commons/a/a3/June_odd-eyed-cat.jpg"
          },
        )
      }

    val List<ChatMember>.isGroup
      get() = size > 2
  }
}
