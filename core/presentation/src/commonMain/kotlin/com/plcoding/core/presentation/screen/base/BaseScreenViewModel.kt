package com.plcoding.core.presentation.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.presentation.event.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

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
  protected open fun getInitialBaseContent() = BaseContent()
  protected open fun getInitialBaseScreenState() = BaseScreenState(
    getInitialContent(),
    getInitialBaseContent(),
  )

  protected fun launch(block: suspend () -> Unit) {
    viewModelScope.launch {
      block()
    }
  }

  protected fun launchLoadable(block: suspend () -> Unit) {
    launch {
      val overlays = setOf(Overlay.Blocker, Overlay.Loader)
      updateBaseContent { copy(overlays = this.overlays?.plus(overlays) ?: overlays) }
      block()
      updateBaseContent { copy(overlays = this.overlays?.minus(overlays)) }
    }
  }

  protected inline fun updateContent(block: Content.() -> Content) {
    return mutableState.update { it.copy(content = block(it.content)) }
  }

  protected inline fun updateBaseContent(block: BaseContent.() -> BaseContent) {
    return mutableState.update { it.copy(baseContent = block(it.baseContent)) }
  }

  protected fun showSnackbar(messageRes: StringResource) {
    return updateBaseContent {
      val overlays = setOf(Overlay.Snackbar(Event(messageRes)))
      copy(overlays = this.overlays?.plus(overlays) ?: overlays)
    }
  }
}
