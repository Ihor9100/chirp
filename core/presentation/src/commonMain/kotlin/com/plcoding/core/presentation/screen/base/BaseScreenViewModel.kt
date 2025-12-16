package com.plcoding.core.presentation.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseScreenViewModel<Content>() : ViewModel() {

  protected abstract fun getInitialContent(): Content

  protected val mutableState = MutableStateFlow(getInitialBaseScreenState())
  val state = mutableState
    .onStart {
      if (!isInitialized) {
        isInitialized = true
        onInitialized()
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000L),
      initialValue = getInitialBaseScreenState()
    )

  private var isInitialized = false

  protected open fun onInitialized() = Unit
  protected open fun getInitialBaseScreenState() = BaseScreenState(getInitialContent())

  protected fun launch(block: suspend () -> Unit) {
    viewModelScope.launch {
      block()
    }
  }

  protected fun launchBlockable(block: suspend () -> Unit) {
    launch {
      mutableState.updateBaseContent { copy(overlays = setOf(Overlay.BLOCKABLE)) }
      block()
      mutableState.updateBaseContent { copy(overlays = null) }
    }
  }

  protected fun launchWithOverlays(overlays: Set<Overlay>, block: suspend () -> Unit) {
    launch {
      mutableState.updateBaseContent { copy(overlays = overlays) }
      block()
      mutableState.updateBaseContent { copy(overlays = null) }
    }
  }

  protected inline fun MutableStateFlow<BaseScreenState<Content>>.updateContent(
    block: Content.() -> Content,
  ) {
    return update { it.copy(content = block(it.content)) }
  }

  protected inline fun MutableStateFlow<BaseScreenState<Content>>.updateBaseContent(
    block: BaseContent.() -> BaseContent,
  ) {
    return update { it.copy(baseContent = block(it.baseContent)) }
  }
}
