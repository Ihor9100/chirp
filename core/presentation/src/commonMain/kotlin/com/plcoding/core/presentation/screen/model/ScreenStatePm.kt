package com.plcoding.core.presentation.screen.model

data class ScreenStatePm<ContentPm>(
  val baseContentPm: BaseContentPm,
  val contentPm: ContentPm,
) {

  constructor(contentPm: ContentPm) : this(
    baseContentPm = BaseContentPm(),
    contentPm = contentPm,
  )

  private inline fun <reified T : Overlay> contains(): Boolean {
    return baseContentPm.overlays.orEmpty().any { it is T }
  }

  fun hasBlocker() = contains<Overlay.Blocker>()
  fun hasLoader() = contains<Overlay.Loader>()
}