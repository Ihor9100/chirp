package com.plcoding.core.presentation.screen.base

import com.plcoding.core.presentation.event.Event
import org.jetbrains.compose.resources.StringResource

sealed interface Overlay {
  data object Blocker : Overlay
  data object Loader : Overlay
  data class Snackbar(val event: Event<StringResource>) : Overlay
}
