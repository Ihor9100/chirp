package com.plcoding.chirp.screen.app

import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenState

data class AppState(
  override val showLoader: Boolean = false,
  val startDestination: Any? = null,
  val removeSplashScreenEvent: Event<Unit>? = null,
  val logoutEvent: Event<Unit>? = null,
) : BaseScreenState<AppState> {

  override fun update(showLoader: Boolean): AppState {
    return copy(showLoader = showLoader)
  }
}
