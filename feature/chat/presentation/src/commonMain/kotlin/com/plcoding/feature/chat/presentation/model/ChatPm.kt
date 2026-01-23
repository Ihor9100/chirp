package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.components.AvatarPm
import com.plcoding.core.presentation.utils.TextProvider

data class ChatPm(
  val id: String,
  val avatarsPm: List<AvatarPm>,
  val title: TextProvider,
  val description: String?,
  val content: String?,
)
