package com.plcoding.core.presentation.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.model.BaseContentPm
import com.plcoding.core.presentation.screen.model.ScreenStatePm
import com.plcoding.core.presentation.screen.model.Overlay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

abstract class BaseScreenViewModel<ContentPm> : ViewModel() {

  protected abstract fun getContentPm(): ContentPm

  val mutableScreenState = MutableStateFlow(ScreenStatePm(getBaseContentPm(), getContentPm()))
  val screenState = mutableScreenState
    .onStart {
      if (!isInitialized) {
        isInitialized = true
        onInitialized()
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000L),
      initialValue = ScreenStatePm(getBaseContentPm(), getContentPm())
    )

  private var isInitialized = false

  protected open fun getBaseContentPm() = BaseContentPm()
  protected open fun getScreenStatePm() = ScreenStatePm(getBaseContentPm(), getContentPm())

  protected open fun onInitialized() = Unit

  protected fun launch(block: suspend () -> Unit) {
    viewModelScope.launch {
      block()
    }
  }

  protected fun launchLoadable(block: suspend () -> Unit) {
    launch {
      val overlays = setOf(Overlay.Blocker, Overlay.Loader(showBackground = false))
      updateBaseContent { copy(overlays = this.overlays?.plus(overlays) ?: overlays) }
      block()
      updateBaseContent { copy(overlays = this.overlays?.minus(overlays)) }
    }
  }

  protected inline fun updateContent(block: ContentPm.() -> ContentPm) {
    return mutableScreenState.update { it.copy(contentPm = block(it.contentPm)) }
  }

  protected inline fun updateBaseContent(block: BaseContentPm.() -> BaseContentPm) {
    return mutableScreenState.update { it.copy(baseContentPm = block(it.baseContentPm)) }
  }

  protected fun showSnackbar(messageRes: StringResource) {
    return updateBaseContent {
      val overlays = setOf(Overlay.Snackbar(Event(messageRes)))
      copy(overlays = this.overlays?.plus(overlays) ?: overlays)
    }
  }
}
