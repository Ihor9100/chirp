package com.plcoding.feature.auth.presentation.screen.register.success

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.ChirError
import com.plcoding.core.designsystem.components.SuccessIcon
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.layout.AdaptiveResultLayout
import com.plcoding.core.designsystem.components.layout.ResultLayout
import com.plcoding.core.designsystem.components.layout.SnackbarLayout
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessScreen(
  viewModel: RegisterSuccessScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  rememberCoroutineScope().launch {
    state.content.snackbarEvent?.consumeAsync {
      snackbarHostState.showSnackbar(getString(it))
    }
  }

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    RegisterSuccessScreenContent(
      content = state.content,
      snackbarHostState = snackbarHostState,
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
fun RegisterSuccessScreenContent(
  content: RegisterSuccessScreenContent,
  snackbarHostState: SnackbarHostState,
  onAction: (RegisterSuccessScreenAction) -> Unit,
) {
  SnackbarLayout(
    modifier = Modifier.fillMaxSize(),
    snackbarHostState = snackbarHostState,
  ) {
    AdaptiveResultLayout(
      modifier = Modifier.fillMaxSize()
    ) {
      ResultLayout(
        icon = { SuccessIcon() },
        title = stringResource(content.titleRes),
        description = content.description?.get(),
        primaryButton = {
          Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(content.primaryButtonTitleRes),
            style = content.primaryButtonStyle,
            isLoading = false,
            enabled = !content.hasOngoingRequest,
            onClick = { onAction(RegisterSuccessScreenAction.PrimaryButtonClick) }
          )
        },
        secondaryButton = {
          Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(content.secondaryButtonTitleRes),
            style = content.secondaryButtonStyle,
            isLoading = false,
            enabled = !content.hasOngoingRequest,
            onClick = { onAction(RegisterSuccessScreenAction.SecondaryButtonClick) }
          )
          if (content.secondaryButtonErrorRes != null) {
            Spacer(Modifier.height(6.dp))
            ChirError(error = stringResource(content.secondaryButtonErrorRes))
          }
        }
      )
    }
  }
}

@Preview
@Composable
private fun RegisterSuccessScreenPreview() {
  Theme {
    RegisterSuccessScreenContent(
      content = RegisterSuccessScreenContent(),
      snackbarHostState = SnackbarHostState(),
      onAction = {},
    )
  }
}