@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.ic_arrow_left
import chirp.feature.chat.presentation.generated.resources.ic_dots
import chirp.feature.chat.presentation.generated.resources.ic_plus
import com.plcoding.core.designsystem.components.DropDownMenuPc
import com.plcoding.core.designsystem.components.button.FloatingButtonPc
import com.plcoding.core.designsystem.components.button.IconButtonPc
import com.plcoding.core.designsystem.components.textfields.MultilineTextFieldPc
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.model.MultilineTextFieldPm
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.screen.model.ScreenStatePm
import com.plcoding.core.presentation.utils.getPaneScaffoldDirective
import com.plcoding.feature.chat.presentation.component.ChatDetailsPc
import com.plcoding.feature.chat.presentation.component.ChatEmptyStatePc
import com.plcoding.feature.chat.presentation.component.ChatHeaderPc
import com.plcoding.feature.chat.presentation.component.ChatPc
import com.plcoding.feature.chat.presentation.component.ChatsHeaderPc
import com.plcoding.feature.chat.presentation.model.ChatPm
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatsScreen(
  navController: NavController,
  viewModel: ChatsScreenViewModel = koinViewModel()
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()

  val deviceConfiguration = getDeviceConfiguration()
  val scaffoldDirective = getPaneScaffoldDirective(deviceConfiguration, currentWindowAdaptiveInfo())
  val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(scaffoldDirective)
  val coroutineScope = rememberCoroutineScope()

  BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
    coroutineScope.launch {
      scaffoldNavigator.navigateBack()
    }
  }

  BaseScreen(
    baseContentPm = state.baseContentPm
  ) {
    Content(
      contentPm = state.contentPm,
      deviceConfiguration = deviceConfiguration,
      scaffoldNavigator = scaffoldNavigator,
      onAction = {
        when (it) {
          is ChatsScreenAction.OnChatClick -> coroutineScope.launch {
            viewModel.openChatDetails(it.chatId)
            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
          }
          is ChatsScreenAction.OnPlusClick -> {
            navController.navigate(ChatRoute.ChatCreate)
          }
          is ChatsScreenAction.OnChatDetailsBackClick -> coroutineScope.launch {
            scaffoldNavigator.navigateBack()
          }
          else -> viewModel.handleAction(it)
        }
      }
    )
  }
}

@Composable
private fun Content(
  contentPm: ChatsScreenContentPm,
  deviceConfiguration: DeviceConfiguration,
  scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
  onAction: (ChatsScreenAction) -> Unit,
) {
  ListDetailPaneScaffold(
    directive = getPaneScaffoldDirective(getDeviceConfiguration(), currentWindowAdaptiveInfo()),
    value = scaffoldNavigator.scaffoldValue,
    listPane = { ChatsPane(contentPm, onAction) },
    detailPane = { ChatDetailsPane(contentPm, deviceConfiguration, onAction) },
  )
}

@Composable
private fun ChatsPane(
  content: ChatsScreenContentPm,
  onAction: (ChatsScreenAction) -> Unit,
) {
  Box {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      ChatsHeaderPc(
        modifier = Modifier.padding(top = 24.dp),
        showMenu = false,
        // TODO:
        avatarPm = AvatarPm.mocks[0],
        onAvatarClick = {},
        onSettingsClick = {},
        onLogoutClick = {},
        onDismissClick = {},
      )
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp),
      ) {
        if (content.chatsEmptyState != null) {
          item {
            ChatEmptyStatePc(
              modifier = Modifier,
              chatEmptyStatePm = content.chatsEmptyState
            )
          }
        } else {
          items(
            items = content.chatsPm,
            key = (ChatPm::id),
          ) {
            ChatPc(
              modifier = Modifier.clickable { onAction(ChatsScreenAction.OnChatClick(it.id)) },
              chatPm = it,
            )
          }
        }
      }
    }
    FloatingButtonPc(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp),
      iconRes = Res.drawable.ic_plus,
      onClick = { onAction(ChatsScreenAction.OnPlusClick) },
    )
  }
}

@Composable
private fun ChatDetailsPane(
  content: ChatsScreenContentPm,
  deviceConfiguration: DeviceConfiguration,
  onAction: (ChatsScreenAction) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .then(
        if (deviceConfiguration.isWideScreen) {
          Modifier
            .padding(vertical = 8.dp)
            .padding(end = 8.dp)
        } else {
          Modifier
            .background(MaterialTheme.colorScheme.surface)
        }
      ),
  ) {
    if (content.chatEmptyState != null) {
      ChatEmptyStatePc(
        modifier = Modifier,
        chatEmptyStatePm = content.chatEmptyState,
      )
    } else {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
          )
          .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        IconButtonPc(
          modifier = Modifier,
          iconRes = Res.drawable.ic_arrow_left,
          onClick = { onAction(ChatsScreenAction.OnChatDetailsBackClick) }
        )
        ChatHeaderPc(
          modifier = Modifier.weight(1f),
          chatHeaderPm = content.chatHeaderPm!!,
        )
        Box {
          IconButtonPc(
            modifier = Modifier,
            iconRes = Res.drawable.ic_dots,
            onClick = { onAction(ChatsScreenAction.OnChatDetailsMenuClick) }
          )
          DropDownMenuPc(
            modifier = Modifier,
            showMenu = !content.dropDownItemsPm.isNullOrEmpty(),
            items = content.dropDownItemsPm.orEmpty(),
            onAction = { onAction(ChatsScreenAction.OnChatDetailsMenuItemClick(it)) },
            onDismiss = { onAction(ChatsScreenAction.OnChatDetailsMenuDismissClick) },
          )
        }
      }
      HorizontalDivider()
      ChatDetailsPc(
        modifier = Modifier
          .weight(1f)
          .background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
          )
          .then(
            if (deviceConfiguration.isWideScreen) {
              Modifier.padding(24.dp)
            } else {
              Modifier.padding(horizontal = 16.dp)
            }
          ),
        chatDetailsPm = content.chatDetailsPm!!,
      )
      MultilineTextFieldPc(
        modifier = Modifier
          .then(
            if (deviceConfiguration.isWideScreen) {
              Modifier.padding(top = 8.dp)
            } else {
              Modifier.padding(16.dp)
            }
          ),
        deviceConfiguration = deviceConfiguration,
        multilineTextFieldPm = MultilineTextFieldPm.mock,
      )
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
  scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
) {
  val screenStatePm = ScreenStatePm(ChatsScreenContentPm.mock)

  Theme(isDarkTheme) {
    BaseScreen(
      baseContentPm = screenStatePm.baseContentPm
    ) {
      Content(
        contentPm = screenStatePm.contentPm,
        scaffoldNavigator = scaffoldNavigator,
        deviceConfiguration = deviceConfiguration,
        onAction = {}
      )
    }
  }
}

@Composable
@Preview
private fun ListLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(),
  )
}

@Composable
@Preview
private fun ListDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(),
  )
}

@Composable
@Preview
private fun DetailLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(
      initialDestinationHistory = listOf(
        ThreePaneScaffoldDestinationItem(
          ListDetailPaneScaffoldRole.Detail
        )
      )
    ),
  )
}

@Composable
@Preview
private fun DetailDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(
      initialDestinationHistory = listOf(
        ThreePaneScaffoldDestinationItem(
          ListDetailPaneScaffoldRole.Detail
        )
      )
    ),
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
private fun DesktopLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(),
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
private fun DesktopDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(),
  )
}
