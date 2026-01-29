package com.plcoding.feature.chat.presentation.screen.chats.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.add
import chirp.feature.chat.presentation.generated.resources.add_members_to_your_chat
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.create_chat
import chirp.feature.chat.presentation.generated.resources.ic_cross
import chirp.feature.chat.presentation.generated.resources.invite_by_username_or_email
import chirp.feature.chat.presentation.generated.resources.no_members
import com.plcoding.core.designsystem.components.HorizontalDividerPc
import com.plcoding.core.designsystem.components.TitleDescriptionPc
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.screen.base.BaseDialogScreen
import com.plcoding.core.presentation.screen.model.ScreenStatePm
import com.plcoding.core.presentation.utils.NavResult
import com.plcoding.feature.chat.presentation.component.ChatMemberPc
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatCreateDialogScreen(
  navController: NavController,
  navResult: NavResult,
  viewModel: ChatCreateDialogScreenViewModel = koinViewModel()
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()

  state.contentPm.chatCreatedEvent?.consume {
    navResult.setResult("arg", it)
    navController.popBackStack()
  }

  BaseDialogScreen(
    baseContentPm = state.baseContentPm,
    deviceConfiguration = getDeviceConfiguration(),
    onDismiss = navController::popBackStack,
  ) {
    Content(
      contentPm = state.contentPm,
      onAction = {
        when (it) {
          ChatCreateDialogScreenAction.OnDismiss -> {
            navResult.setResult("arg", "Result")
            navController.popBackStack()
          }
          else -> viewModel.onAction(it)
        }
      }
    )
  }
}

@Composable
private fun Content(
  contentPm: ChatCreateDialogScreenContentPm,
  onAction: (ChatCreateDialogScreenAction) -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 16.dp,
          vertical = 12.dp,
        ),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = stringResource(Res.string.create_chat),
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleMedium,
      )
      IconButton(
        onClick = { onAction(ChatCreateDialogScreenAction.OnDismiss) }
      ) {
        Icon(
          modifier = Modifier.size(24.dp),
          imageVector = vectorResource(Res.drawable.ic_cross),
          contentDescription = null,
          tint = MaterialTheme.colorScheme.extended.textSecondary,
        )
      }
    }
    HorizontalDividerPc()
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 16.dp,
          vertical = 12.dp,
        ),
      horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      TextFieldPlain(
        modifier = Modifier.weight(1f),
        textFieldState = contentPm.searchTextFieldState,
        inputPlaceholder = stringResource(Res.string.invite_by_username_or_email),
      )
      ButtonPc(
        text = stringResource(Res.string.add),
        style = ButtonPcStyle.SECONDARY,
        onClick = { onAction(ChatCreateDialogScreenAction.OnAddClick) }
      )
    }
    contentPm.chatMemberPm?.apply {
      ChatMemberPc(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .padding(bottom = 12.dp),
        chatMemberPm = this,
      )
    }
    HorizontalDividerPc()
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 16.dp,
          vertical = 12.dp,
        ),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      if (contentPm.chatMembersPm.isEmpty()) {
        item {
          TitleDescriptionPc(
            modifier = Modifier.padding(vertical = 24.dp),
            titleRes = Res.string.no_members,
            descriptionRes = Res.string.add_members_to_your_chat,
          )
        }
      } else {
        items(
          count = contentPm.chatMembersPm.size,
          key = { contentPm.chatMembersPm[it].id },
        ) { index ->
          ChatMemberPc(
            modifier = Modifier,
            chatMemberPm = contentPm.chatMembersPm[index],
          )
        }
      }
    }
    HorizontalDividerPc()
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 16.dp,
          vertical = 20.dp,
        ),
      horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ButtonPc(
        modifier = Modifier,
        text = stringResource(Res.string.cancel),
        style = ButtonPcStyle.SECONDARY,
      )
      ButtonPc(
        modifier = Modifier,
        text = stringResource(Res.string.create_chat),
        style = ButtonPcStyle.PRIMARY,
        onClick = { onAction(ChatCreateDialogScreenAction.OnCreateDialogClick) }
      )
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
  deviceConfiguration: DeviceConfiguration,
) {
  val screenStatePm = ScreenStatePm(
    contentPm = ChatCreateDialogScreenContentPm(
      chatMembersPm = ChatMemberPm.mocks,
    ),
  )

  Theme(isDarkTheme) {
    BaseDialogScreen(
      baseContentPm = screenStatePm.baseContentPm,
      deviceConfiguration = deviceConfiguration,
      onDismiss = {},
    ) {
      Content(
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
fun MobileDarkPreview() {
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
fun TabletLightPreview() {
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
