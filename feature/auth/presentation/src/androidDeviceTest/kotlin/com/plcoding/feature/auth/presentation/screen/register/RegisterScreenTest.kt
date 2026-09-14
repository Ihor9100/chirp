package com.plcoding.feature.auth.presentation.screen.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_invalid_email
import com.plcoding.feature.auth.presentation.screen.register.RegisterScreenTestTag.LOGIN_BUTTON
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun fieldsAndButtonsRenderers() {
    composeTestRule.setContent {
      RegisterScreenContent(
        uiState = RegisterScreenUiState(),
        onAction = {}
      )
    }

    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.USERNAME_TEXT_FIELD.value)
      .assertIsDisplayed()
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.EMAIL_TEXT_FIELD.value)
      .assertIsDisplayed()
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_TEXT_FIELD.value)
      .assertIsDisplayed()
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.REGISTER_BUTTON.value)
      .assertIsDisplayed()
    composeTestRule
      .onNodeWithTag(LOGIN_BUTTON.value)
      .assertIsDisplayed()
  }

  @Test
  fun clickOnRegisterSendsRegisterAction() {
    var capturedAction: RegisterScreenAction? = null
    composeTestRule.setContent {
      RegisterScreenContent(
        uiState = RegisterScreenUiState(),
        onAction = { action -> capturedAction = action }
      )
    }

    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.REGISTER_BUTTON.value)
      .performClick()

    assertEquals(RegisterScreenAction.OnRegisterClick, capturedAction)
  }

  @Test
  fun clickOnLoginSendsLoginAction() {
    var capturedAction: RegisterScreenAction? = null
    composeTestRule.setContent {
      RegisterScreenContent(
        uiState = RegisterScreenUiState(),
        onAction = { action -> capturedAction = action }
      )
    }

    composeTestRule
      .onNodeWithTag(LOGIN_BUTTON.value)
      .performClick()

    assertEquals(RegisterScreenAction.OnLoginClick, capturedAction)
  }

  @Test
  fun showErrorIfUiStateHasIt() {
    val errorRes = Res.string.error_invalid_email
    val uiState = RegisterScreenUiState(errorRes = errorRes)

    composeTestRule.setContent {
      RegisterScreenContent(
        uiState = uiState,
        onAction = { }
      )
    }

    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.ERROR.value)
      .assertTextEquals("This is not a valid email")
  }

  @Test
  fun clickOnPasswordSecureToggleSendsSecureToggleAction() {
    var capturedAction: RegisterScreenAction? = null
    composeTestRule.setContent {
      RegisterScreenContent(
        uiState = RegisterScreenUiState(),
        onAction = { action -> capturedAction = action }
      )
    }

    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_SECURE_ICON.value)
      .performClick()

    assertEquals(RegisterScreenAction.OnPasswordSecureIconClick, capturedAction)
  }

  @Test
  fun usernameTextFieldInputWorks() {
    val uiState = RegisterScreenUiState()
    val username = "Ihor9100"
    composeTestRule.setContent {
      RegisterScreenContent(
        uiState = uiState,
        onAction = { }
      )
    }

    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.USERNAME_TEXT_FIELD.value)
      .performTextInput(username)

    assertEquals(username, uiState.usernameState.text.toString())
  }

  @Test
  fun passwordTextFieldSecuresInput() {
    val password = "Naruto*19890702"
    var uiState by mutableStateOf(RegisterScreenUiState(passwordIsSecureMode = true))
    composeTestRule.setContent {
      RegisterScreenContent(uiState) {
        if (it is RegisterScreenAction.OnPasswordSecureIconClick) {
          uiState = uiState
            .copy(passwordIsSecureMode = !uiState.passwordIsSecureMode)
        }
      }
    }

    // Act
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_TEXT_FIELD.value)
      .performTextInput(password)

    // Assert
    assertEquals(password, uiState.passwordState.text.toString())
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_SECURE_ICON.value)
      .assertContentDescriptionEquals("Show password")

    // Act
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_SECURE_ICON.value)
      .performClick()

    // Assert
    assertEquals(password, uiState.passwordState.text.toString())
    assertFalse(uiState.passwordIsSecureMode)
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_SECURE_ICON.value)
      .assertContentDescriptionEquals("Hide password")
  }
}