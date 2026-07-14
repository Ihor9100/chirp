package com.plcoding.feature.chat.presentation.screen.chats.manage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseDialogScreen
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenAction
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatManageDialogScreen(
  navController: NavController,
  viewModel: ChatManageDialogScreenViewModel = koinViewModel()
) {
  val state by viewModel.screenUiState.collectAsStateWithLifecycle()

  state.uiState.chatUpdatedEvent?.consume {
    navController.popBackStack()
  }

  BaseDialogScreen(
    baseUiState = state.baseUiState,
    deviceConfiguration = getDeviceConfiguration(),
    onDismiss = navController::popBackStack,
  ) {
    BaseChatDialogScreenContent(
      uiState = state.uiState,
      onAction = {
        when (it) {
          BaseChatDialogScreenAction.OnDismiss -> {
            navController.popBackStack()
          }
          else -> viewModel.onAction(it)
        }
      }
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
  deviceConfiguration: DeviceConfiguration,
) {
  val screenUiState = ScreenUiState(
    uiState = ChatManageDialogUiState(
      foundChatMembersUi = ChatMemberUi.mocks,
    ),
  )

  Theme(isDarkTheme) {
    BaseDialogScreen(
      baseUiState = screenUiState.baseUiState,
      deviceConfiguration = deviceConfiguration,
      onDismiss = {},
    ) {
      BaseChatDialogScreenContent(
        uiState = screenUiState.uiState,
        onAction = {}
      )
    }
  }
}

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
private fun MobileLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
private fun MobileDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
private fun TabletLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
private fun TabletDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}
