@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.auth.presentation.screen.register

import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_account_exists
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegisterScreenViewModelTest {

  lateinit var fakeAuthRepository: FakeAuthRepository
  lateinit var viewModel: RegisterScreenViewModel

  val username = "Ihor9100"
  val email = "ihor.bohdanovskyi@gmail.com"
  val password = "Naruto*19890702"

  fun initViewModel() {
    fakeAuthRepository = FakeAuthRepository()
    viewModel = RegisterScreenViewModel(fakeAuthRepository)
  }

  fun updateTextFields(
    username: String = this.username,
    email: String = this.email,
    password: String = this.password
  ) {
    with(viewModel.screenUiState.value.uiState) {
      usernameState.edit { append(username) }
      emailState.edit { append(email) }
      passwordState.edit { append(password) }
    }
  }

  @Test
  fun `password secure toggle switches mode back and forth`() {
    runViewModelTest {
      initViewModel()
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

  @Test
  fun `register with invalid email shows email error`() {
    runViewModelTest {
      initViewModel()
      backgroundScope.launch { viewModel.screenUiState.collect() }
      updateTextFields(email = "ihor.bohdanovskyi.gmail.com")
      advanceUntilIdle()

      viewModel.onAction(RegisterScreenAction.OnPrimaryButtonClick)

      advanceUntilIdle()
      assertEquals(true, viewModel.screenUiState.value.uiState.emailIsError)
      assertTrue(viewModel.screenUiState.value.uiState.emailBottomTitleRes != null)
      assertEquals(0, fakeAuthRepository.registerCallCount)
    }
  }

  @Test
  fun `register triggers register request`() {
    runViewModelTest {
      initViewModel()
      updateTextFields()
      fakeAuthRepository.registerResult = Result.Success(Unit)

      viewModel.onAction(RegisterScreenAction.OnPrimaryButtonClick)

      advanceUntilIdle()
      assertEquals(1, fakeAuthRepository.registerCallCount)
      assertEquals(username, fakeAuthRepository.lastUsername)
      assertEquals(email, fakeAuthRepository.lastEmail)
      assertEquals(password, fakeAuthRepository.lastPassword)
    }
  }

  @Test
  fun `register emits success event`() {
    runViewModelTest {
      initViewModel()
      updateTextFields()
      fakeAuthRepository.registerResult = Result.Success(Unit)
      val event = backgroundScope.async { viewModel.event.first() }

      viewModel.onAction(RegisterScreenAction.OnPrimaryButtonClick)

      advanceUntilIdle()
      assertEquals(RegisterScreenEvent.Success(email), event.await())
    }
  }

  @Test
  fun `register conflict error shows conflict error`() {
    runViewModelTest {
      initViewModel()
      fakeAuthRepository.registerResult = Result.Failure(DataError.Remote.CONFLICT)
      backgroundScope.launch { viewModel.screenUiState.collect() }
      advanceUntilIdle()

      updateTextFields()
      viewModel.onAction(RegisterScreenAction.OnPrimaryButtonClick)

      advanceUntilIdle()
      assertEquals(Res.string.error_account_exists, viewModel.screenUiState.value.uiState.errorRes)
    }
  }

  private fun runViewModelTest(body: suspend TestScope.() -> Unit) {
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
