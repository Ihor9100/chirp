package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.components.AvatarPm

data class ChatParticipantPm(
  val id: String,
  val avatarPm: AvatarPm,
  val fullName: String,
)
