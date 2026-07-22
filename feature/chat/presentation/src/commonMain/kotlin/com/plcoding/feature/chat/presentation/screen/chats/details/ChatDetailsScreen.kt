@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
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
import com.plcoding.core.designsystem.components.DropDownMenu
import com.plcoding.core.designsystem.components.button.IconButton
import com.plcoding.core.designsystem.components.textfields.MultilineTextField
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.feature.chat.presentation.component.ChatEmptyState
import com.plcoding.feature.chat.presentation.component.ChatHeader
import com.plcoding.feature.chat.presentation.component.ChatMessages
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatDetailsScreen(
  chatId: String?,
  navController: NavController,
  scaffoldNavigator: ThreePaneScaffoldNavigator<*>,
  viewModel: ChatDetailsScreenViewModel = koinViewModel()
) {
  val screenUiState by viewModel.screenUiState.collectAsStateWithLifecycle()
  val deviceConfiguration = getDeviceConfiguration()
  val coroutineScope = rememberCoroutineScope()
  val lazyListState = rememberLazyListState()

  BackHandler(scaffoldNavigator.canNavigateBack()) {
    coroutineScope.launch {
      scaffoldNavigator.navigateBack()
    }
  }

  LaunchedEffect(chatId) {
    // To prevent EmptyState blinking during navigation animation
    if (chatId == null) delay(100)
    viewModel.loadChat(chatId)
  }

  LaunchedEffect(screenUiState.uiState.openChatManageEvent) {
    screenUiState.uiState.openChatManageEvent?.consume {
      navController.navigate(ChatRoute.ChatManage(it))
    }
  }

  LaunchedEffect(screenUiState.uiState.leaveChatEvent) {
    screenUiState.uiState.leaveChatEvent?.runAsync {
      scaffoldNavigator.navigateBack()
    }
  }

  LaunchedEffect(screenUiState.uiState.scrollToStart) {
    screenUiState.uiState.scrollToStart?.consumeAsync {
      // Temporary workaround for scrolling after messages appears in the list
      delay(100)
      lazyListState.animateScrollToItem(0)
    }
  }

  LazyListScrollObserver(
    lazyListState = lazyListState,
    isPageLoading = screenUiState.uiState.isPageLoading,
    isLastPage = screenUiState.uiState.isLastPage,
    onLoadMore = { viewModel.handleAction(ChatDetailsScreenAction.OnLoadMore) },
    onScrollToStartChanged = {
      viewModel.handleAction(
        ChatDetailsScreenAction.OnScrollToStartChanged(it)
      )
    }
  )

  BaseScreen(
    modifier = getBaseScreenModifier(deviceConfiguration),
    baseUiState = screenUiState.baseUiState,
    backgroundColor = null,
  ) {
    Content(
      uiState = screenUiState.uiState,
      deviceConfiguration = deviceConfiguration,
      lazyListState = lazyListState,
      onAction = {
        when (it) {
          is ChatDetailsScreenAction.OnBackClick -> coroutineScope.launch {
            if (scaffoldNavigator.canNavigateBack()) {
              scaffoldNavigator.navigateBack()
            } else {
              scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, null)
              scaffoldNavigator.navigateBack()
            }
          }
          else -> {
            viewModel.handleAction(it)
          }
        }
      },
    )
  }
}

@Composable
private fun Content(
  uiState: ChatDetailsScreenUiState,
  deviceConfiguration: DeviceConfiguration,
  lazyListState: LazyListState,
  onAction: (ChatDetailsScreenAction) -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (uiState.chatEmptyStateUi != null) {
      ChatEmptyState(
        modifier = Modifier
          .fillMaxSize()
          .background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
          ),
        chatEmptyStateUi = uiState.chatEmptyStateUi,
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
        IconButton(
          modifier = Modifier,
          iconRes = Res.drawable.ic_arrow_left,
          onClick = { onAction(ChatDetailsScreenAction.OnBackClick) }
        )
        if (uiState.chatHeaderUi != null) {
          ChatHeader(
            modifier = Modifier.weight(1f),
            chatHeaderUi = uiState.chatHeaderUi,
          )
        }
        Box {
          IconButton(
            modifier = Modifier,
            iconRes = Res.drawable.ic_dots,
            onClick = { onAction(ChatDetailsScreenAction.OnMenuClick) }
          )
          DropDownMenu(
            modifier = Modifier,
            showMenu = !uiState.dropDownItemsUi.isNullOrEmpty(),
            items = uiState.dropDownItemsUi.orEmpty(),
            onAction = { onAction(ChatDetailsScreenAction.OnMenuItemClick(it)) },
            onDismiss = { onAction(ChatDetailsScreenAction.OnMenuDismiss) },
          )
        }
      }
      HorizontalDivider()
      if (uiState.chatMessagesUi != null) {
        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
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
        ) {
          ChatMessages(
            modifier = Modifier,
            chatMessagesUi = uiState.chatMessagesUi,
            lazyListState = lazyListState,
            longPressedMessageId = uiState.longPressedMessageId,
            isPageLoading = uiState.isPageLoading,
            pageLoadingError = uiState.pageLoadingError,
            onLongClick = { onAction(ChatDetailsScreenAction.OnMessageLongClick(it)) },
            onMenuItemClick = { onAction(ChatDetailsScreenAction.OnMessageMenuItemClick(it)) },
            onMenuDismiss = { onAction(ChatDetailsScreenAction.OnMessageMenuDismiss) },
            onMessageRetryClick = { onAction(ChatDetailsScreenAction.OnMessageRetryClick(it)) },
            onPageRetryClick = { onAction(ChatDetailsScreenAction.OnPageRetryClick) }
          )
          if (uiState.showScrollToStartButton) {
            IconButton(
              modifier = Modifier.align(Alignment.BottomEnd),
              imageVector = Icons.Default.ArrowDownward,
              onClick = { onAction(ChatDetailsScreenAction.OnScrollToStartClick) }
            )
          }
        }
      }
      MultilineTextField(
        modifier = Modifier
          .then(
            if (deviceConfiguration.isWideScreen) {
              Modifier.padding(top = 8.dp)
            } else {
              Modifier.padding(16.dp)
            }
          ),
        deviceConfiguration = deviceConfiguration,
        multilineTextFieldPm = uiState.multilineTextFieldUi,
        onClick = { onAction(ChatDetailsScreenAction.OnSendClick) },
      )
    }
  }
}

@Composable
private fun getBaseScreenModifier(deviceConfiguration: DeviceConfiguration): Modifier {
  return if (deviceConfiguration.isWideScreen) {
    Modifier
      .background(MaterialTheme.colorScheme.extended.surfaceLower)
      .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
  } else {
    Modifier
      .background(MaterialTheme.colorScheme.surface)
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
  scaffoldNavigator: ThreePaneScaffoldNavigator<*>,
) {
  val screenUiState = ScreenUiState(ChatDetailsScreenUiState.mock)

  Theme(isDarkTheme) {
    BaseScreen(
      modifier = getBaseScreenModifier(deviceConfiguration),
      baseUiState = screenUiState.baseUiState,
      backgroundColor = MaterialTheme.colorScheme.surface,
    ) {
      Content(
        uiState = screenUiState.uiState,
        deviceConfiguration = deviceConfiguration,
        lazyListState = rememberLazyListState(),
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
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(),
  )
}

@Composable
@Preview
private fun ListDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(),
  )
}

@Composable
@Preview
private fun DetailLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(
      initialDestinationHistory = listOf(
        ThreePaneScaffoldDestinationItem(
          pane = ListDetailPaneScaffoldRole.Detail,
          contentKey = ""
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
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(
      initialDestinationHistory = listOf(
        ThreePaneScaffoldDestinationItem(
          pane = ListDetailPaneScaffoldRole.Detail,
          contentKey = ""
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
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(),
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
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(),
  )
}
