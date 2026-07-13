package com.plcoding.core.designsystem.model

data class AvatarUi(
  val initials: String,
  val imageUrl: String?,
  val avatarSizeUi: AvatarSizeUi,
) {

  companion object {
    val mocks
      get() = listOf(
        AvatarUi(
          initials = "IB",
          imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a3/June_odd-eyed-cat.jpg",
          avatarSizeUi = AvatarSizeUi.MEDIUM,
        ),
        AvatarUi(
          initials = "BI",
          imageUrl = "https://upload.wikimedia.org/wikipedia/commons/3/3a/Cat03.jpg",
          avatarSizeUi = AvatarSizeUi.MEDIUM,
        ),
      )
  }
}
