package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AvatarPm

data class LocalDetailsPm(
  val name: String,
) : ChatDetailsPm {

  companion object {
    val mock get() = LocalDetailsPm("Today")
  }
}
