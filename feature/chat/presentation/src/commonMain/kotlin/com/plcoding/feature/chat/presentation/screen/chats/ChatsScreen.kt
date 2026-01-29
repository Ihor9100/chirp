@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.ic_plus
import chirp.feature.chat.presentation.generated.resources.img_chat
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import chirp.feature.chat.presentation.generated.resources.send
import com.plcoding.core.designsystem.components.TitleDescriptionPc
import com.plcoding.core.designsystem.components.button.FloatingActionButton
import com.plcoding.core.designsystem.components.textfields.MultilineTextField
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.screen.model.ScreenStatePm
import com.plcoding.core.presentation.utils.NavResult
import com.plcoding.core.presentation.utils.getPaneScaffoldDirective
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.component.ChatPc
import com.plcoding.feature.chat.presentation.component.ChatsHeaderPc
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
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
      scaffoldNavigator = scaffoldNavigator,
      deviceConfiguration = deviceConfiguration,
      onAction = {
        when (it) {
          is ChatsScreenAction.OnChatsClick -> coroutineScope.launch {
            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
          }
          is ChatsScreenAction.OnChatsCreateClick -> navController.navigate(
            ChatRoute.ChatCreate,
          )
        }
      }
    )
  }
}

@Composable
private fun Content(
  contentPm: ChatsScreenContentPm,
  scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
  deviceConfiguration: DeviceConfiguration,
  onAction: (ChatsScreenAction) -> Unit,
) {
  ListDetailPaneScaffold(
    directive = getPaneScaffoldDirective(getDeviceConfiguration(), currentWindowAdaptiveInfo()),
    value = scaffoldNavigator.scaffoldValue,
    listPane = { ChatsPane(contentPm, scaffoldNavigator, onAction) },
    detailPane = { ChatDetailsPane(contentPm, scaffoldNavigator, deviceConfiguration) },
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
      showMenu = false,
      avatarPm = AvatarPm.mock,
      onAvatarClick = {},
      onSettingsClick = {},
      onLogoutClick = {},
      onDismissClick = {},
    )
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center,
    ) {
      if (content.chatsPm.isEmpty()) {
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
      } else {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(top = 16.dp),
        ) {
          items(content.chatsPm.size) { index ->
            ChatPc(
              modifier = Modifier,
              chatPm = content.chatsPm[index]
            )
          }
        }
      }
      FloatingActionButton(
        modifier = Modifier
          .padding(16.dp)
          .align(Alignment.BottomEnd),
        onClick = { onAction(ChatsScreenAction.OnChatsCreateClick) },
      ) {
        Image(
          imageVector = vectorResource(Res.drawable.ic_plus),
          contentDescription = null,
          colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
        )
      }
    }
  }
}

@Composable
private fun ChatDetailsPane(
  content: ChatsScreenContentPm,
  scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
  deviceConfiguration: DeviceConfiguration,
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
        text = content.chat?.id ?: "Empty",
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
  val screenStatePm = ScreenStatePm(ChatsScreenContentPm.mock)

  Theme(isDarkTheme) {
    BaseScreen(
      baseContentPm = screenStatePm.baseContentPm
    ) {
      Content(
        contentPm = screenStatePm.contentPm,
        scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(),
        deviceConfiguration = deviceConfiguration,
        onAction = {}
      )
    }
  }
}

@Composable
@Preview
private fun MobileLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT
  )
}

@Composable
@Preview
private fun MobileDarkPreview() {
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
private fun DesktopLightPreview() {
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
private fun DesktopDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP
  )
}
