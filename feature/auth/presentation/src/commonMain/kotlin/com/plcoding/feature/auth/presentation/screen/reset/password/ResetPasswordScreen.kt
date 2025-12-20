package com.plcoding.feature.auth.presentation.screen.reset.password

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResetPasswordScreen(
  viewModel: ResetPasswordScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  rememberCoroutineScope().launch {
    state.content.resetSuccessEvent?.consumeAsync {
      val result = snackbarHostState.showSnackbar(getString(it))
      viewModel.onAction(ResetPasswordScreenAction.OnSnackbarDisappeared(result))
    }
  }

  state.content.navigateToLoginEvent?.run(openLogin)

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    ResetPasswordScreenContent(
      content = state.content,
      onAction = viewModel::onAction
    )
  }
}

@Composable
fun ResetPasswordScreenContent(
  content: ResetPasswordScreenContent,
  onAction: (ResetPasswordScreenAction) -> Unit,
) {

}

@Preview
@Composable
private fun ResetPasswordScreenPreview() {
  ChirpTheme {
    ResetPasswordScreenContent(
      content = ResetPasswordScreenContent(),
      onAction = {}
    )
  }
}