package com.plcoding.feature.chat.presentation.screen.chat.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.create_chat
import chirp.feature.chat.presentation.generated.resources.ic_cross
import chirp.feature.chat.presentation.generated.resources.invite_by_username_or_email
import com.plcoding.core.designsystem.components.Avatar
import com.plcoding.core.designsystem.components.AvatarPm
import com.plcoding.core.designsystem.components.AvatarSize
import com.plcoding.core.designsystem.components.HorizontalDivider
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.titleXSmall
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.screen.base.BaseContent
import com.plcoding.core.presentation.screen.base.BaseScreenDialogContent
import com.plcoding.core.presentation.screen.base.BaseScreenState
import com.plcoding.core.presentation.screen.base.Overlay
import com.plcoding.feature.chat.presentation.model.ChatParticipantPm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatCreateScreen(
  navController: NavController,
  viewModel: ChatCreateScreenViewModel = ChatCreateScreenViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  BaseScreenDialogContent(
    baseContent = state.baseContent,
    deviceConfiguration = getDeviceConfiguration(),
    onDismiss = navController::popBackStack,
  ) {
    ChatCreateScreenContent(
      content = state.content,
      onAction = viewModel::onAction
    )
  }
}

@Composable
fun ChatCreateScreenContent(
  content: ChatCreateScreenContent,
  onAction: (ChatCreateScreenAction) -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxWidth()
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
        onClick = { onAction(ChatCreateScreenAction.OnDismiss) }
      ) {
        Icon(
          modifier = Modifier.size(24.dp),
          imageVector = vectorResource(Res.drawable.ic_cross),
          contentDescription = null,
          tint = MaterialTheme.colorScheme.extended.textSecondary,
        )
      }
    }
    HorizontalDivider()
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
        textFieldState = content.searchTextFieldState,
        inputPlaceholder = stringResource(Res.string.invite_by_username_or_email),
      )
      Button(
        text = stringResource(Res.string.add),
        style = ButtonStyle.SECONDARY,
      )
    }
    HorizontalDivider()
    Box(
      modifier = Modifier.padding(16.dp)
    ) {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
      ) {
        items(
          count = content.chatParticipantsPm.size,
          key = { content.chatParticipantsPm[it].id },
        ) { index ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            val chatParticipantPm = content.chatParticipantsPm[index]

            Avatar(
              avatarPm = chatParticipantPm.avatarPm
            )
            Text(
              text = chatParticipantPm.fullName,
              color = MaterialTheme.colorScheme.extended.textPrimary,
              style = MaterialTheme.typography.titleXSmall,
            )
          }
        }
      }
    }
    HorizontalDivider()
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
      Button(
        modifier = Modifier,
        text = stringResource(Res.string.cancel),
        style = ButtonStyle.SECONDARY,
      )
      Button(
        modifier = Modifier,
        text = stringResource(Res.string.create_chat),
        style = ButtonStyle.PRIMARY,
      )
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
  deviceConfiguration: DeviceConfiguration,
) {
  val baseScreenState = BaseScreenState(
    content = ChatCreateScreenContent(
      chatParticipantsPm = listOf(
        ChatParticipantPm(
          id = "1",
          avatarPm = AvatarPm(
            fullName = "Ihor A",
            imageUrl = "1",
            avatarSize = AvatarSize.MEDIUM,
          ),
          fullName = "Ihor Bohdanovskyi"
        ),
        ChatParticipantPm(
          id = "2",
          avatarPm = AvatarPm(
            fullName = "Ihor B",
            imageUrl = "1",
            avatarSize = AvatarSize.MEDIUM,
          ),
          fullName = "Ihor Bohdanovskyi"
        ),
      ),
    ),
    baseContent = BaseContent(setOf(Overlay.LOADABLE))
  )

  Theme(isDarkTheme) {
    BaseScreenDialogContent(
      baseContent = baseScreenState.baseContent,
      deviceConfiguration = deviceConfiguration,
      onDismiss = {},
    ) {
      ChatCreateScreenContent(
        content = baseScreenState.content,
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
fun MobileLightPreview() {
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
fun TabletDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}
