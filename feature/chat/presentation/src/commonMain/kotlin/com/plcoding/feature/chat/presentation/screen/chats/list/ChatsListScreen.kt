@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.ic_plus
import com.plcoding.core.designsystem.components.button.FloatingButton
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.feature.chat.presentation.component.Chat
import com.plcoding.feature.chat.presentation.component.ChatEmptyState
import com.plcoding.feature.chat.presentation.component.ChatsHeader
import com.plcoding.feature.chat.presentation.model.ChatUi
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatsListScreen(
  navController: NavController,
  scaffoldNavigator: ThreePaneScaffoldNavigator<String>,
  viewModel: ChatsListScreenViewModel = koinViewModel()
) {
  val state by viewModel.screenUiState.collectAsStateWithLifecycle()
  val coroutineScope = rememberCoroutineScope()
  val currentDestination = scaffoldNavigator.currentDestination

  LaunchedEffect(currentDestination) {
    if (currentDestination?.pane != ListDetailPaneScaffoldRole.Detail) {
      viewModel.handleAction(ChatsListScreenAction.OnChatClick(null))
    }
  }

  BaseScreen(
    baseUiState = state.baseUiState
  ) {
    Content(
      uiState = state.uiState,
      onAction = {
        when (it) {
          is ChatsListScreenAction.OnChatClick -> coroutineScope.launch {
            viewModel.handleAction(it)
            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, it.chatId)
          }
          is ChatsListScreenAction.OnPlusClick -> {
            navController.navigate(ChatRoute.ChatCreate)
          }
        }
      }
    )
  }
}

@Composable
private fun Content(
  uiState: ChatsListScreenUiState,
  onAction: (ChatsListScreenAction) -> Unit,
) {
  Box {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      ChatsHeader(
        modifier = Modifier.padding(top = 24.dp),
        showMenu = false,
        // TODO:
        avatarUi = AvatarUi.mocks[0],
        onAvatarClick = {},
        onSettingsClick = {},
        onLogoutClick = {},
        onDismissClick = {},
      )
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp),
      ) {
        if (uiState.chatEmptyStateUi != null) {
          item {
            ChatEmptyState(
              modifier = Modifier,
              chatEmptyStateUi = uiState.chatEmptyStateUi
            )
          }
        } else {
          items(
            items = uiState.chatsUi,
            key = (ChatUi::id),
          ) {
            Chat(
              modifier = Modifier.clickable { onAction(ChatsListScreenAction.OnChatClick(it.id)) },
              chatUi = it,
            )
          }
        }
      }
    }
    FloatingButton(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp),
      iconRes = Res.drawable.ic_plus,
      onClick = { onAction(ChatsListScreenAction.OnPlusClick) },
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
  scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
) {
  val screenUiState = ScreenUiState(ChatsListScreenUiState.mock)

  Theme(isDarkTheme) {
    BaseScreen(
      baseUiState = screenUiState.baseUiState
    ) {
      Content(
        uiState = screenUiState.uiState,
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
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(),
  )
}

@Composable
@Preview
private fun ListDarkPreview() {
  Themed(
    isDarkTheme = true,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(),
  )
}

@Composable
@Preview
private fun DetailLightPreview() {
  Themed(
    isDarkTheme = false,
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
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(),
  )
}
