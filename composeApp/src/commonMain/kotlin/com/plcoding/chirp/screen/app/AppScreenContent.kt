package com.plcoding.chirp.screen.app

import com.plcoding.core.presentation.event.Event

data class AppScreenContent(
  val startDestination: Any? = null,
  val removeSplashScreenEvent: Event<Unit>? = null,
  val logoutEvent: Event<Unit>? = null,
)
