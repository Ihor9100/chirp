package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.components.AvatarPm

data class ChatPm(
  val id: String,
  val avatarsPm: List<AvatarPm>,
  val title: String,
  val description: String?,
  val content: String,
)
