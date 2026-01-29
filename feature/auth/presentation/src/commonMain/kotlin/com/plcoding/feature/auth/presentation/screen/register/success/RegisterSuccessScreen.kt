package com.plcoding.feature.auth.presentation.screen.register.success

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.ChirError
import com.plcoding.core.designsystem.components.SuccessIcon
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.layout.ResultLayout
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveResultLayout
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.screen.model.ScreenStatePm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessScreen(
  viewModel: RegisterSuccessScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()

  BaseScreen(
    baseContentPm = state.baseContentPm
  ) {
    Content(
      contentPm = state.contentPm,
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
  contentPm: RegisterSuccessScreenContentPm,
  onAction: (RegisterSuccessScreenAction) -> Unit,
) {
  AdaptiveResultLayout(
    modifier = Modifier.fillMaxSize()
  ) {
    ResultLayout(
      icon = { SuccessIcon() },
      title = stringResource(contentPm.titleRes),
      description = contentPm.description?.get(),
      primaryButton = {
        ButtonPc(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(contentPm.primaryButtonTitleRes),
          style = contentPm.primaryButtonPcStyle,
          isLoading = false,
          isEnabled = !contentPm.hasOngoingRequest,
          onClick = { onAction(RegisterSuccessScreenAction.PrimaryButtonClick) }
        )
      },
      secondaryButton = {
        ButtonPc(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(contentPm.secondaryButtonTitleRes),
          style = contentPm.secondaryButtonPcStyle,
          isLoading = false,
          isEnabled = !contentPm.hasOngoingRequest,
          onClick = { onAction(RegisterSuccessScreenAction.SecondaryButtonClick) }
        )
        if (contentPm.secondaryButtonErrorRes != null) {
          Spacer(Modifier.height(6.dp))
          ChirError(error = stringResource(contentPm.secondaryButtonErrorRes))
        }
      }
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenStatePm = ScreenStatePm(RegisterSuccessScreenContentPm())

  Theme(isDarkTheme) {
    BaseScreen(
      baseContentPm = screenStatePm.baseContentPm
    ) {
      Content(
        contentPm = screenStatePm.contentPm,
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
