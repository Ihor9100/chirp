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
          profilePictureUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a3/June_odd-eyed-cat.jpg",
        )
      }
  }
}
