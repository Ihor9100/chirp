@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.auth.presentation.screen.register

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.Test
import kotlin.test.assertEquals

class RegisterScreenViewModelTest {

  @Test
  fun `password secure toggle switches mode back and forth`() {
    runViewModelTest {
      val authRepository = FakeAuthRepository()
      val viewModel = RegisterScreenViewModel(authRepository)
      val getIsPasswordSecureMode = { viewModel.screenUiState.value.uiState.passwordIsSecureMode }
      val isPasswordSecureMode = getIsPasswordSecureMode()
      backgroundScope.launch { viewModel.screenUiState.collect() }
      advanceUntilIdle()

      viewModel.onAction(RegisterScreenAction.OnTextFieldSecureToggleClick)

      advanceUntilIdle()
      assertEquals(isPasswordSecureMode, !getIsPasswordSecureMode())

      viewModel.onAction(RegisterScreenAction.OnTextFieldSecureToggleClick)

      advanceUntilIdle()
      assertEquals(isPasswordSecureMode, getIsPasswordSecureMode())
    }
  }

  private fun runViewModelTest(body: TestScope.() -> Unit) {
    val dispatcher = StandardTestDispatcher()
    Dispatchers.setMain(dispatcher)
    try {
      runTest(dispatcher) {
        body()
      }
    } finally {
      Dispatchers.resetMain()
    }
  }
}
