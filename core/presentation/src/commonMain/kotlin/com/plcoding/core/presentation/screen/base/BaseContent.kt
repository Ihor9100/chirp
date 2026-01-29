package com.plcoding.core.presentation.screen.base

data class BaseContent(
  val overlays: Set<Overlay>? = null,
) {

  companion object {
    val mock
      get() = BaseContent(
        overlays = setOf(Overlay.Blocker, Overlay.Loader(showBackground = true))
      )
  }
}
