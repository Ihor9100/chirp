package com.plcoding.core.presentation.model

data class BaseUiState(
  val overlays: Set<Overlay>? = null,
) {

  companion object {
    val mock
      get() = BaseUiState(
        overlays = setOf(Overlay.Blocker, Overlay.Loader(showBackground = true))
      )
  }
}