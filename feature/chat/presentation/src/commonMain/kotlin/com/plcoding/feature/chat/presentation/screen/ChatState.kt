package com.plcoding.feature.chat.presentation.screen

import com.plcoding.core.presentation.screen.base.BaseScreenState

data class ChatState(
  override val showLoader: Boolean = false,
  val paramOne: String = "default",
  val paramTwo: List<String> = emptyList(),
) : BaseScreenState<ChatState> {

  override fun update(showLoader: Boolean): ChatState {
    return copy(showLoader = showLoader)
  }
}