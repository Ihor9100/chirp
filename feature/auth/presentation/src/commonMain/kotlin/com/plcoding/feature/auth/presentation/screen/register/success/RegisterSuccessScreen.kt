package com.plcoding.feature.auth.presentation.screen.register.success

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.account_successfully_created
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import com.plcoding.core.designsystem.components.Error
import com.plcoding.core.designsystem.components.SuccessIcon
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.components.layout.ResultLayout
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveResultLayout
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessScreen(
  viewModel: RegisterSuccessScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.screenUiState.collectAsStateWithLifecycle()

  BaseScreen(
    baseUiState = state.baseUiState,
    backgroundColor = MaterialTheme.colorScheme.background,
    isSafeDrawing = false,
  ) {
    Content(
      uiState = state.uiState,
      onAction = {
        when (it) {
          is RegisterSuccessScreenAction.PrimaryButtonClick -> openLogin()
          else -> viewModel.onAction(it)
        }
      },
    )
  }
}

@Composable
private fun Content(
  uiState: RegisterSuccessUiState,
  onAction: (RegisterSuccessScreenAction) -> Unit,
) {
  AdaptiveResultLayout(
    modifier = Modifier
      .testTag(RegisterSuccessScreenTestTag.SCREEN.value)
      .fillMaxSize()
  ) {
    ResultLayout(
      icon = { SuccessIcon() },
      title = stringResource(Res.string.account_successfully_created),
      description = uiState.description?.get(),
      primaryButton = {
        Button(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(Res.string.log_in),
          style = ButtonStyle.PRIMARY,
          isLoading = false,
          isEnabled = !uiState.hasOngoingRequest,
          onClick = { onAction(RegisterSuccessScreenAction.PrimaryButtonClick) }
        )
      },
      secondaryButton = {
        Button(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(Res.string.resend_verification_email),
          style = ButtonStyle.SECONDARY,
          isLoading = false,
          isEnabled = !uiState.hasOngoingRequest,
          onClick = { onAction(RegisterSuccessScreenAction.SecondaryButtonClick) }
        )
        if (uiState.secondaryButtonErrorRes != null) {
          Spacer(Modifier.height(6.dp))
          Error(error = stringResource(uiState.secondaryButtonErrorRes))
        }
      }
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenUiState = ScreenUiState(RegisterSuccessUiState())

  Theme(isDarkTheme) {
    BaseScreen(
      baseUiState = screenUiState.baseUiState,
      backgroundColor = MaterialTheme.colorScheme.background,
    ) {
      Content(
        uiState = screenUiState.uiState,
        onAction = {}
      )
    }
  }
}

@Preview
@Composable
private fun LightPreview() {
  Themed(
    isDarkTheme = false,
  )
}

@Preview
@Composable
private fun DarkPreview() {
  Themed(
    isDarkTheme = true
  )
}

enum class RegisterSuccessScreenTestTag(val value: String) {
  SCREEN("register_success_screen"),
}
