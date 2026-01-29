package com.plcoding.core.presentation.screen.base

import kotlin.reflect.KClass

data class BaseScreenState<Content>(
  val content: Content,
  val baseContent: BaseContent = BaseContent(),
) {

  private inline fun <reified T : Overlay> contains(): Boolean {
    return baseContent.overlays.orEmpty().any { it is T }
  }

  fun hasBlocker() = contains<Overlay.Blocker>()
  fun hasLoader() = contains<Overlay.Loader>()
}