package com.plcoding.core.presentation.model

data class ScreenUiState<ContentUi>(
  val baseUiState: BaseUiState,
  val uiState: ContentUi,
) {

  constructor(uiState: ContentUi) : this(
    baseUiState = BaseUiState(),
    uiState = uiState,
  )

  private inline fun <reified T : Overlay> contains(): Boolean {
    return baseUiState.overlays.orEmpty().any { it is T }
  }

  fun hasBlocker() = contains<Overlay.Blocker>()
  fun hasLoader() = contains<Overlay.Loader>()
}
