package com.plcoding.core.presentation.screen.base

data class BaseScreenState<Content>(
  val content: Content,
  val baseContent: BaseContent = BaseContent(),
) {

  fun hasBlocker(): Boolean {
    return baseContent.overlays?.contains(Overlay.Blocker) == true
  }

  fun hasLoader(): Boolean {
    return baseContent.overlays?.contains(Overlay.Loader) == true
  }
}