package com.plcoding.feature.chat.presentation.model

data class DateDividerPm(
  val date: String,
) : ChatDetailsPm {

  companion object {
    val mock get() = DateDividerPm("Today")
  }
}
