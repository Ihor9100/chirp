package com.plcoding.feature.chat.presentation.model

data class DateDividerUi(
  val date: String,
) : ChatDetailsUi {

  companion object {
    val mock get() = DateDividerUi("Today")
  }
}
