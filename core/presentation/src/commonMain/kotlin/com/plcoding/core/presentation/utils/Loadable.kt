package com.plcoding.core.presentation.utils

interface Loadable<State: Loadable<State>> {
  val showLoader: Boolean
  fun showLoader(show: Boolean): State
}
