package com.plcoding.core.presentation.screen.base

data class BaseScreenState<Content>(
  val content: Content,
  val baseContent: BaseContent = BaseContent(),
) {

  fun isBlocking(): Boolean {
    return baseContent.overlays?.contains(Overlay.BLOCKABLE) == true
  }

  fun isLoading(): Boolean {
    return baseContent.overlays?.contains(Overlay.LOADABLE) == true
  }
}