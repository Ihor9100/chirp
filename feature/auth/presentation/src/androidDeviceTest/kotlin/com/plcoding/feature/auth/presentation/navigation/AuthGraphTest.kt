package com.plcoding.feature.auth.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.auth.presentation.di.authPresentationDiModule
import com.plcoding.feature.auth.presentation.screen.login.LoginScreenTestTag
import com.plcoding.feature.auth.presentation.screen.register.RegisterScreenTestTag
import com.plcoding.feature.auth.presentation.screen.register.success.RegisterSuccessScreenTestTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class AuthGraphTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  fun setupKoin() {
    stopKoin()
    startKoin {
      modules(
        authPresentationDiModule,
        module {
          single<AuthRepository> { AuthRepositoryFake() }
          single<PreferencesRepository> { PreferencesRepositoryFake() }
        },
      )
    }
  }

  @Test
  fun authGraphOpensLoginScreen() {
    setupKoin()

    composeTestRule.setContent {
      val navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = AuthRoute.Graph,
      ) {
        authGraph(navController) {}
      }
    }

    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.SCREEN.value)
      .assertIsDisplayed()
  }

  @Test
  fun fromLoginToRegisterSuccess() {
    setupKoin()
    composeTestRule.setContent {
      val navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = AuthRoute.Graph,
      ) {
        authGraph(navController) {}
      }
    }
    val email = "ihor9100@example.com"

    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.REGISTER_BUTTON.value)
      .performClick()
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
      composeTestRule
        .onNodeWithTag(RegisterSuccessScreenTestTag.SCREEN.value)
        .isDisplayed()
    }
    composeTestRule
      .onNodeWithTag(RegisterSuccessScreenTestTag.SCREEN.value)
      .assertIsDisplayed()
    composeTestRule
      .onNodeWithText(email, substring = true)
      .assertIsDisplayed()
  }

  @Test
  fun registerSuccessOpensLogin() {
    setupKoin()
    composeTestRule.setContent {
      val navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = AuthRoute.Graph,
      ) {
        authGraph(navController) {}
      }
      LaunchedEffect(Unit) {
        navController.navigate(AuthRoute.RegisterSuccess("ihor9100@example.com"))
      }
    }
    composeTestRule.waitUntil(5_000) {
      composeTestRule
        .onNodeWithTag(RegisterSuccessScreenTestTag.SCREEN.value)
        .isDisplayed()
    }

    composeTestRule
      .onNodeWithTag(RegisterSuccessScreenTestTag.LOGIN_BUTTON.value)
      .performClick()

    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.SCREEN.value)
      .assertIsDisplayed()
  }
}

private class AuthRepositoryFake : AuthRepository {
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
    return Result.Success(Unit)
  }

  override suspend fun resendVerificationEmail(email: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun verifyEmail(token: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }
}

private class PreferencesRepositoryFake : PreferencesRepository {
  override fun observeAuthInfo(): Flow<AuthInfo?> {
    return flowOf(null)
  }

  override suspend fun saveAuthInfo(authInfo: AuthInfo?) {
    TODO("Not yet implemented")
  }

  override suspend fun saveData(data: String) {
    TODO("Not yet implemented")
  }

}