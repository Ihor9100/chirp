package com.plcoding.feature.chat.presentation.screen.chats.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.screen.base.BaseDialogScreen
import com.plcoding.core.presentation.model.ScreenStatePm
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenAction
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatCreateDialogScreen(
  navController: NavController,
  viewModel: ChatCreateDialogScreenViewModel = koinViewModel()
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()

  state.contentPm.chatCreatedEvent?.consume {
    navController.popBackStack()
  }

  BaseDialogScreen(
    baseContentPm = state.baseContentPm,
    deviceConfiguration = getDeviceConfiguration(),
    onDismiss = navController::popBackStack,
  ) {
    BaseChatDialogScreenContent(
      contentPm = state.contentPm,
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
  val screenStatePm = ScreenStatePm(
    contentPm = ChatCreateDialogScreenContentPm(
      foundChatMembersPm = ChatMemberPm.mocks,
    ),
  )

  Theme(isDarkTheme) {
    BaseDialogScreen(
      baseContentPm = screenStatePm.baseContentPm,
      deviceConfiguration = deviceConfiguration,
      onDismiss = {},
    ) {
      BaseChatDialogScreenContent(
        contentPm = screenStatePm.contentPm,
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
