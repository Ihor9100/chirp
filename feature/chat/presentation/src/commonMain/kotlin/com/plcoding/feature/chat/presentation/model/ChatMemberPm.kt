package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.components.AvatarPm

data class ChatMemberPm(
  val id: String,
  val fullName: String,
  val avatarPm: AvatarPm,
)
