package com.plcoding.core.designsystem.model

data class AvatarPm(
  val initials: String,
  val imageUrl: String?,
  val avatarSizePm: AvatarSizePm,
) {

  companion object {
    val mock
      get() = AvatarPm(
        initials = "IB",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a3/June_odd-eyed-cat.jpg",
        avatarSizePm = AvatarSizePm.MEDIUM,
      )
  }
}
