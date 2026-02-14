package com.plcoding.feature.chat.presentation.model

import androidx.compose.runtime.Composable

interface ChatDetailsPm {

  companion object {
    val mocks
      get() = listOf(
        DateDividerPm.mock,
        LocalMessagePm.mock,
        RemoteMessagePm.mock,
      )
  }
}