package com.plcoding.chirp.screen.app

import com.plcoding.core.presentation.event.Event

data class AppState(
  val startDestination: Any? = null,
  val removeSplashScreenEvent: Event<Unit>? = null,
  val logoutEvent: Event<Unit>? = null,
)
