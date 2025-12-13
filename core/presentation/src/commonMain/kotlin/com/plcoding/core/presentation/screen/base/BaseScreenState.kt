package com.plcoding.core.presentation.screen.base

interface BaseScreenState<State> {
  val showLoader: Boolean
  fun update(showLoader: Boolean): State
}
