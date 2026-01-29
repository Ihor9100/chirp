package com.plcoding.core.presentation.screen.model

import com.plcoding.core.presentation.event.Event
import org.jetbrains.compose.resources.StringResource

sealed interface Overlay {
  data object Blocker : Overlay
  data class Loader(val showBackground: Boolean) : Overlay
  data class Snackbar(val event: Event<StringResource>) : Overlay
}