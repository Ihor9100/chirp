@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import com.plcoding.core.presentation.screen.base.BaseScreenState
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.getDeviceConfiguration
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen(
  viewModel: ChatScreenViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    ChatScreenContent(
      content = state.content,
      deviceConfiguration = getDeviceConfiguration(),
      onAction = viewModel::onAction
    )
  }
}

@Composable
private fun ChatScreenContent(
  content: ChatScreenContent,
  deviceConfiguration: DeviceConfiguration,
  onAction: (ChatScreenAction) -> Unit,
) {
  val isTabletop = currentWindowAdaptiveInfo().windowPosture.isTabletop
  val scaffoldDirective = PaneScaffoldDirective(
    maxHorizontalPartitions = when (deviceConfiguration) {
      DeviceConfiguration.MOBILE_PORTRAIT,
      DeviceConfiguration.MOBILE_LANDSCAPE,
      DeviceConfiguration.TABLET_PORTRAIT -> 1
      DeviceConfiguration.TABLET_LANDSCAPE,
      DeviceConfiguration.DESKTOP -> 2
    },
    horizontalPartitionSpacerSize = 0.dp,
    maxVerticalPartitions = if (isTabletop) 2 else 1,
    verticalPartitionSpacerSize = if (isTabletop) 24.dp else 0.dp,
    defaultPanePreferredWidth = 360.dp,
    excludedBounds = emptyList(),
  )
  val navigator = rememberListDetailPaneScaffoldNavigator(scaffoldDirective)
  val coroutineScope = rememberCoroutineScope()

  BackHandler(enabled = navigator.canNavigateBack()) {
    coroutineScope.launch {
      navigator.navigateBack()
    }
  }

  Theme {
    ListDetailPaneScaffold(
      directive = scaffoldDirective,
      value = navigator.scaffoldValue,
      listPane = { ChatScreenListContent(content, navigator, onAction) },
      detailPane = { ChatScreenDetailsContent(content, navigator, onAction) },
    )
  }
}

@Composable
private fun ChatScreenListContent(
  content: ChatScreenContent,
  navigator: ThreePaneScaffoldNavigator<Any>,
  onAction: (ChatScreenAction) -> Unit,
) {
  val coroutineScope = rememberCoroutineScope()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    items(100) { index ->
      Text(
        text = "Item $index",
        modifier = Modifier
          .clickable {
            coroutineScope.launch {
              onAction(ChatScreenAction.OnChatClick("$index"))
              navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
            }
          }
      )
    }
  }
}

@Composable
private fun ChatScreenDetailsContent(
  content: ChatScreenContent,
  navigator: ThreePaneScaffoldNavigator<Any>,
  onAction: (ChatScreenAction) -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = content.chatId ?: "Empty"
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val baseScreenState = BaseScreenState(ChatScreenContent())

  Theme(isDarkTheme) {
    BaseScreenContent(
      baseContent = baseScreenState.baseContent
    ) {
      ChatScreenContent(
        content = baseScreenState.content,
        deviceConfiguration = getDeviceConfiguration(),
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
