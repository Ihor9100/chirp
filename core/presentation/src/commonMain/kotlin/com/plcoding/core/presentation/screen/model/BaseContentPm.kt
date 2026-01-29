package com.plcoding.core.presentation.screen.model

data class BaseContentPm(
  val overlays: Set<Overlay>? = null,
) {

  companion object {
    val mock
      get() = BaseContentPm(
        overlays = setOf(Overlay.Blocker, Overlay.Loader(showBackground = true))
      )
  }
}