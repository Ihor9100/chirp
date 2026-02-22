@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
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
import chirp.feature.chat.presentation.generated.resources.img_chat
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import chirp.feature.chat.presentation.generated.resources.send
import com.plcoding.core.designsystem.components.TitleDescriptionPc
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
import com.plcoding.core.presentation.utils.NavResult
import com.plcoding.core.presentation.utils.getPaneScaffoldDirective
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.component.ChatDetailsPc
import com.plcoding.feature.chat.presentation.component.ChatHeaderPc
import com.plcoding.feature.chat.presentation.component.ChatPc
import com.plcoding.feature.chat.presentation.component.ChatsHeaderPc
import com.plcoding.feature.chat.presentation.model.ChatDetailsPm
import com.plcoding.feature.chat.presentation.model.ChatHeaderPm
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatsScreen(
  navController: NavController,
  navResult: NavResult,
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

  navResult.addListener<Chat>("arg") {
    viewModel.onResult(it)
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
            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
          }
          is ChatsScreenAction.OnPlusClick -> {
            viewModel.onAction {
              navController.navigate(
                ChatRoute.ChatCreate,
              )
            }
          }
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
    listPane = { ChatsPane(contentPm, scaffoldNavigator, onAction) },
    detailPane = { ChatDetailsPane(contentPm, deviceConfiguration, scaffoldNavigator) },
  )
}

@Composable
private fun ChatsPane(
  content: ChatsScreenContentPm,
  navigator: ThreePaneScaffoldNavigator<Any>,
  onAction: (ChatsScreenAction) -> Unit,
) {
  val coroutineScope = rememberCoroutineScope()

  Column(
    modifier = Modifier.fillMaxSize(),
  ) {
    ChatsHeaderPc(
      modifier = Modifier.padding(top = 24.dp),
      showMenu = false,
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
      if (content.chatsPm.isEmpty()) {
        item {
          Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Image(
              painter = painterResource(Res.drawable.img_chat),
              contentDescription = null,
            )
            TitleDescriptionPc(
              titleRes = Res.string.no_messages,
              descriptionRes = Res.string.no_messages_subtitle
            )
          }
        }
      } else {
        items(content.chatsPm.size) { index ->
          ChatPc(
            modifier = Modifier.clickable { onAction(ChatsScreenAction.OnChatClick("1")) },
            chatPm = content.chatsPm[index]
          )
        }
      }
    }
    FloatingButtonPc(
      modifier = Modifier.padding(16.dp),
      iconRes = Res.drawable.ic_plus,
      onClick = { onAction(ChatsScreenAction.OnPlusClick) },
    )
  }
}

@Composable
private fun ChatDetailsPane(
  content: ChatsScreenContentPm,
  deviceConfiguration: DeviceConfiguration,
  scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
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
        onClick = {}
      )
      ChatHeaderPc(
        modifier = Modifier.weight(1f),
        chatHeaderPm = ChatHeaderPm.mock,
      )
      IconButtonPc(
        modifier = Modifier,
        iconRes = Res.drawable.ic_dots,
        onClick = {}
      )
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
      chatDetailsPm = ChatDetailsPm.mocks,
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
