package com.plcoding.feature.auth.presentation.screen.register

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class, ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun loginClickOpensLogin() {
    val authRepositoryFake = AuthRepositoryFake()
    val viewModel = RegisterScreenViewModel(authRepositoryFake)
    var openLoginCalled = false
    composeTestRule.setContent {
      RegisterScreen(
        viewModel = viewModel,
        openRegisterSuccess = {},
        openLogin = { openLoginCalled = true }
      )
    }

    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.LOGIN_BUTTON.value)
      .performClick()

    assertTrue(openLoginCalled)
  }

  @Test
  fun successRegistrationOpensRegisterSuccess() {
    val authRepositoryFake = AuthRepositoryFake()
    val viewModel = RegisterScreenViewModel(authRepositoryFake)
    val openRegisterSuccessCalled = AtomicBoolean(false)
    val email = "ihor9100@example.com"
    var capturedEmail: AtomicReference<String>? = null
    composeTestRule.setContent {
      RegisterScreen(
        viewModel = viewModel,
        openRegisterSuccess = {
          capturedEmail = AtomicReference(it)
          openRegisterSuccessCalled.set(true)
        },
        openLogin = {}
      )
    }

    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.USERNAME_TEXT_FIELD.value)
      .performTextInput("Ihor9100")
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.EMAIL_TEXT_FIELD.value)
      .performTextInput(email)
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_TEXT_FIELD.value)
      .performTextInput("Password123!")
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.REGISTER_BUTTON.value)
      .performClick()

    composeTestRule.waitUntil(5_000) {
      openRegisterSuccessCalled.get()
    }
    assertTrue(openRegisterSuccessCalled.get())
    assertEquals(email, capturedEmail?.get())
  }

  @Test
  fun conflictRegistrationShowsConflictError() {
    val authRepositoryFake = AuthRepositoryFake()
    authRepositoryFake.registerResult = Result.Failure(DataError.Remote.CONFLICT)
    val viewModel = RegisterScreenViewModel(authRepositoryFake)
    var openRegisterSuccessCalled = false
    composeTestRule.setContent {
      RegisterScreen(
        viewModel = viewModel,
        openRegisterSuccess = { openRegisterSuccessCalled = true },
        openLogin = {}
      )
    }

    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.USERNAME_TEXT_FIELD.value)
      .performTextInput("Ihor9100")
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.EMAIL_TEXT_FIELD.value)
      .performTextInput("ihor9100@example.com")
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_TEXT_FIELD.value)
      .performTextInput("Password123!")
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.REGISTER_BUTTON.value)
      .performClick()

    composeTestRule.waitUntilAtLeastOneExists(
      matcher = hasTestTag(RegisterScreenTestTag.ERROR.value),
      timeoutMillis = 5_000,
    )

    assertFalse(openRegisterSuccessCalled)
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.ERROR.value)
      .assertTextEquals("An account with that email or username already exists")
  }
}

private class AuthRepositoryFake : AuthRepository {

  var registerResult: Result<Unit, DataError.Remote> = Result.Success(Unit)

  override suspend fun login(
    email: String,
    password: String
  ): Result<AuthInfo, DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun logout(refreshToken: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun forgotPassword(email: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun resetPassword(
    password: String,
    token: String
  ): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun register(
    username: String,
    email: String,
    password: String
  ): Empty<DataError.Remote> {
    return registerResult
  }

  override suspend fun resendVerificationEmail(email: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun verifyEmail(token: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }
}
