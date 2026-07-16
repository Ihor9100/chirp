@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chats

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.utils.getPaneScaffoldDirective
import com.plcoding.feature.chat.presentation.screen.chats.details.ChatDetailsScreen
import com.plcoding.feature.chat.presentation.screen.chats.list.ChatsListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatsScreen(
  navController: NavController,
  viewModel: ChatsScreenViewModel = koinViewModel()
) {
  val screenUiState by viewModel.screenUiState.collectAsStateWithLifecycle()
  val deviceConfiguration = getDeviceConfiguration()
  val scaffoldDirective = getPaneScaffoldDirective(deviceConfiguration, currentWindowAdaptiveInfo())
  val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(scaffoldDirective)

  BaseScreen(
    baseUiState = screenUiState.baseUiState
  ) {
    Content(
      navController = navController,
      scaffoldNavigator = scaffoldNavigator,
    )
  }
}

@Composable
private fun Content(
  navController: NavController,
  scaffoldNavigator: ThreePaneScaffoldNavigator<String>,
) {
  ListDetailPaneScaffold(
    directive = getPaneScaffoldDirective(getDeviceConfiguration(), currentWindowAdaptiveInfo()),
    value = scaffoldNavigator.scaffoldValue,
    listPane = {
      AnimatedPane {
        ChatsListScreen(
          navController = navController,
          scaffoldNavigator = scaffoldNavigator,
        )
      }
    },
    detailPane = {
      AnimatedPane {
        ChatDetailsScreen(
          chatId = scaffoldNavigator.currentDestination?.contentKey,
          navController = navController,
          scaffoldNavigator = scaffoldNavigator,
        )
      }
    },
  )
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
  scaffoldNavigator: ThreePaneScaffoldNavigator<String>,
) {
  Theme(isDarkTheme) {
    Content(
      navController = rememberNavController(),
      scaffoldNavigator = scaffoldNavigator,
    )
  }
}

@Composable
@Preview
private fun ListLightPreview() {
  Themed(
    isDarkTheme = false,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(),
  )
}

@Composable
@Preview
private fun ListDarkPreview() {
  Themed(
    isDarkTheme = true,
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(),
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
    scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>(),
  )
}
