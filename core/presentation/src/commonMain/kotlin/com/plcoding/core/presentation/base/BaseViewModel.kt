package com.plcoding.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.presentation.utils.Loadable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO: add super type like: WithLoader, Loadable and function like: runLoadable(execute: suspend () -> Unit) wh
abstract class BaseViewModel<State : Loadable<State>>() : ViewModel() {

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

  protected fun runLoadable(run: suspend () -> Unit) {
    viewModelScope.launch {
      mutableState.update { it.showLoader(true) }
      run()
      mutableState.update { it.showLoader(false) }
    }
  }
}
