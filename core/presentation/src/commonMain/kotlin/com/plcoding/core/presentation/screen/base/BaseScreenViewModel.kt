package com.plcoding.core.presentation.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.model.BaseUiState
import com.plcoding.core.presentation.model.Overlay
import com.plcoding.core.presentation.model.ScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

abstract class BaseScreenViewModel<UiState> : ViewModel() {

  protected abstract fun getUiState(): UiState

  val mutableScreenUiState = MutableStateFlow(getScreenUiState())
  val screenState = mutableScreenUiState
    .onStart {
      if (!isInitialized) {
        isInitialized = true
        onInitialize()
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000L),
      initialValue = getScreenUiState()
    )

  private var isInitialized = false

  protected open fun getBaseUiState() = BaseUiState()
  protected open fun getScreenUiState() = ScreenUiState(getBaseUiState(), getUiState())

  protected open fun onInitialize() = Unit

  protected fun launchLoadable(block: suspend () -> Unit) {
    viewModelScope.launch {
      val overlays = setOf(Overlay.Blocker, Overlay.Loader(showBackground = false))
      updateBaseUiState { copy(overlays = this.overlays?.plus(overlays) ?: overlays) }
      block()
      updateBaseUiState { copy(overlays = this.overlays?.minus(overlays)) }
    }
  }

  protected inline fun updateUiState(block: UiState.() -> UiState) {
    return mutableScreenUiState.update { it.copy(uiState = block(it.uiState)) }
  }

  protected inline fun updateBaseUiState(block: BaseUiState.() -> BaseUiState) {
    return mutableScreenUiState.update { it.copy(baseUiState = block(it.baseUiState)) }
  }

  protected fun showSnackbar(messageRes: StringResource, onDismiss: (() -> Unit)? = null) {
    return updateBaseUiState {
      val overlays = setOf(Overlay.Snackbar(Event(messageRes), onDismiss))
      copy(overlays = this.overlays?.plus(overlays) ?: overlays)
    }
  }
}
