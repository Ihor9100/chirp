package com.plcoding.core.designsystem.model

data class AvatarPm(
  val initials: String,
  val imageUrl: String?,
  val avatarSize: AvatarSize,
) {

  companion object {
    val mock
      get() = AvatarPm(
        initials = "IB",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a3/June_odd-eyed-cat.jpg",
        avatarSize = AvatarSize.MEDIUM,
      )
  }
}
