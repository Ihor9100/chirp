@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.send
import com.plcoding.core.designsystem.components.textfields.MultilineTextField
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import com.plcoding.core.presentation.screen.base.BaseScreenState
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.getDeviceConfiguration
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen(
  navController: NavController,
  viewModel: ChatScreenViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    ChatScreenContent(
      content = state.content,
      deviceConfiguration = getDeviceConfiguration(),
      onAction = {
        navController.navigate(ChatRoute.ChatCreate)
      }
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

  ListDetailPaneScaffold(
    directive = scaffoldDirective,
    value = navigator.scaffoldValue,
    listPane = { ChatScreenListContent(content, navigator, deviceConfiguration, onAction) },
    detailPane = { ChatScreenDetailsContent(content, navigator, deviceConfiguration, onAction) },
  )
}

@Composable
private fun ChatScreenListContent(
  content: ChatScreenContent,
  navigator: ThreePaneScaffoldNavigator<Any>,
  deviceConfiguration: DeviceConfiguration,
  onAction: (ChatScreenAction) -> Unit,
) {
  val coroutineScope = rememberCoroutineScope()

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    items(100) { index ->
      Text(
        text = "Item $index",
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            coroutineScope.launch {
              onAction(ChatScreenAction.OnChatClick("$index"))
              navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
            }
          },
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleLarge
      )
    }
  }
}

@Composable
private fun ChatScreenDetailsContent(
  content: ChatScreenContent,
  navigator: ThreePaneScaffoldNavigator<Any>,
  deviceConfiguration: DeviceConfiguration,
  onAction: (ChatScreenAction) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          color = MaterialTheme.colorScheme.surface,
          shape = RoundedCornerShape(16.dp)
        )
        .weight(1f),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = content.chatId ?: "Empty",
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.displayLarge
      )
    }
    MultilineTextField(
      deviceConfiguration = deviceConfiguration,
      textFieldState = TextFieldState(),
      inputPlaceholder = "Placeholder",
      buttonTitleRes = Res.string.send,
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
) {
  val baseScreenState = BaseScreenState(ChatScreenContent())

  Theme(isDarkTheme) {
    BaseScreenContent(
      baseContent = baseScreenState.baseContent
    ) {
      ChatScreenContent(
        content = baseScreenState.content,
        deviceConfiguration = deviceConfiguration,
        onAction = {}
      )
    }
  }
}

@Composable
@Preview
fun MobileLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT
  )
}

@Composable
@Preview
fun MobileDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
fun DesktopLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.DESKTOP
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
fun DesktopDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP
  )
}
