package com.plcoding.core.presentation.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseScreenViewModel<State : BaseScreenState<State>>() : ViewModel() {

  protected abstract fun getInitialState(): State

  protected val mutableState = MutableStateFlow(getInitialState())
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
      initialValue = getInitialState()
    )

  private var isInitialized = false

  protected open fun onInitialized() = Unit

  protected fun launchLoadable(block: suspend () -> Unit) {
    viewModelScope.launch {
      mutableState.update { it.update(true) }
      block()
      mutableState.update { it.update(false) }
    }
  }
}
