package com.plcoding.feature.chat.presentation.model

data class LocalMessagePm(
  val name: String,
) : ChatDetailsPm {

  companion object {
    val mock get() = LocalMessagePm("Today")
  }
}
