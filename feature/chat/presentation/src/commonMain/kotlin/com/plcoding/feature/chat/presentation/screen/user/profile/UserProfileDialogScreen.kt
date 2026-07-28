@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.user.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import chirp.core.designsystem.generated.resources.ic_upload
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.contact_support_to_change_email
import chirp.feature.chat.presentation.generated.resources.current_password
import chirp.feature.chat.presentation.generated.resources.delete
import chirp.feature.chat.presentation.generated.resources.email
import chirp.feature.chat.presentation.generated.resources.new_password
import chirp.feature.chat.presentation.generated.resources.password
import chirp.feature.chat.presentation.generated.resources.password_hint
import chirp.feature.chat.presentation.generated.resources.profile_image
import chirp.feature.chat.presentation.generated.resources.profile_settings
import chirp.feature.chat.presentation.generated.resources.save
import chirp.feature.chat.presentation.generated.resources.upload_image
import com.plcoding.core.designsystem.components.Avatar
import com.plcoding.core.designsystem.components.HorizontalDivider
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.components.button.IconButton
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseDialogScreen
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import com.plcoding.feature.chat.presentation.screen.chats.manage.ChatManageDialogUiState
import com.plcoding.feature.chat.presentation.screen.user.profile.component.UserProfileAdaptiveSection
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import chirp.core.designsystem.generated.resources.Res as CoreRes

@Composable
fun UserProfileDialogScreen(
  navController: NavController,
  viewModel: UserProfileDialogScreenViewModel = koinViewModel()
) {
  val screenUiState by viewModel.screenUiState.collectAsStateWithLifecycle()
  val deviceConfiguration = getDeviceConfiguration()

  BaseDialogScreen(
    baseUiState = screenUiState.baseUiState,
    deviceConfiguration = deviceConfiguration,
    onDismiss = navController::popBackStack,
  ) {
    Content(
      uiState = screenUiState.uiState,
      onAction = viewModel::handleAction,
    )
  }
}

@Composable
private fun Content(
  uiState: UserProfileDialogScreenUiState,
  onAction: (UserProfileDialogScreenAction) -> Unit,
) {
  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.Start,
  ) {
    Row(
      modifier = Modifier
        .padding(top = 16.dp)
        .padding(horizontal = 16.dp)
    ) {
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = uiState.username,
          color = MaterialTheme.colorScheme.extended.textPrimary,
          style = MaterialTheme.typography.titleMedium,
        )
        Text(
          text = stringResource(Res.string.profile_settings),
          color = MaterialTheme.colorScheme.extended.textSecondary,
          style = MaterialTheme.typography.bodySmall,
        )
      }
      IconButton(
        imageVector = Icons.Default.Close,
        onClick = { onAction(UserProfileDialogScreenAction.OnCloseClick) },
      )
    }
    HorizontalDivider()
    UserProfileAdaptiveSection(
      modifier = Modifier.padding(horizontal = 16.dp),
      sectionRes = Res.string.profile_image,
    ) {
      Row(
        verticalAlignment = Alignment.Top,
      ) {
        if (uiState.avatarUi != null) {
          Avatar(
            avatarUi = uiState.avatarUi,
          )
        }
        Spacer(Modifier.height(20.dp))
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          Button(
            text = stringResource(Res.string.upload_image),
            style = ButtonStyle.SECONDARY,
            isLoading = false,
            onClick = { onAction(UserProfileDialogScreenAction.OnUploadImageClick) },
            leadingIcon = {
              Icon(
                imageVector = vectorResource(CoreRes.drawable.ic_upload),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.extended.textPrimary,
              )
            }
          )
          Button(
            text = stringResource(Res.string.delete),
            style = ButtonStyle.DESTRUCTIVE_SECONDARY,
            onClick = { onAction(UserProfileDialogScreenAction.OnDeleteClick) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
              )
            }
          )
        }
      }
    }
    HorizontalDivider()
    UserProfileAdaptiveSection(
      modifier = Modifier.padding(horizontal = 16.dp),
      sectionRes = Res.string.email,
    ) {
      TextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = null,
        textFieldState = uiState.emailTextFieldState,
        inputPlaceholder = stringResource(Res.string.email),
        bottomTitle = stringResource(Res.string.contact_support_to_change_email),
        keyboardType = KeyboardType.Text,
        isError = uiState.isEmailError,
      )
    }
    HorizontalDivider()
    UserProfileAdaptiveSection(
      modifier = Modifier.padding(horizontal = 16.dp),
      sectionRes = Res.string.password,
    ) {
      TextFieldPassword(
        topTitle = null,
        textFieldState = uiState.currentPasswordTextFieldState,
        inputPlaceholder = stringResource(Res.string.current_password),
        bottomTitle = null,
        isError = uiState.newPasswordError != null,
        isSecureMode = uiState.isCurrentPasswordSecureMode,
        onSecureToggleClick = { onAction(UserProfileDialogScreenAction.OnCurrentPasswordEyeClick) }
      )
      TextFieldPassword(
        topTitle = null,
        textFieldState = uiState.newPasswordTextFieldState,
        inputPlaceholder = stringResource(Res.string.new_password),
        bottomTitle = uiState.newPasswordError ?: stringResource(Res.string.password_hint),
        isError = uiState.newPasswordError != null,
        isSecureMode = uiState.isNewPasswordSecureMode,
        onSecureToggleClick = { onAction(UserProfileDialogScreenAction.OnNewPasswordEyeClick) }
      )
    }
    Row(
      modifier = Modifier
        .navigationBarsPadding()
        .padding(horizontal = 16.dp)
        .padding(bottom = 16.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
    ) {
      Button(
        text = stringResource(Res.string.cancel),
        style = ButtonStyle.SECONDARY,
        onClick = { onAction(UserProfileDialogScreenAction.OnSecondaryButtonClick) }
      )
      Button(
        text = stringResource(Res.string.save),
        style = ButtonStyle.PRIMARY,
        isEnabled = uiState.isPositiveButtonEnable,
        onClick = { onAction(UserProfileDialogScreenAction.OnPrimaryButtonClick) }
      )
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
  deviceConfiguration: DeviceConfiguration,
) {
  val screenUiState = ScreenUiState(
    uiState = ChatManageDialogUiState(
      foundChatMembersUi = ChatMemberUi.mocks,
    ),
  )

  Theme(isDarkTheme) {
    BaseDialogScreen(
      baseUiState = screenUiState.baseUiState,
      deviceConfiguration = deviceConfiguration,
      onDismiss = {},
    ) {

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
private fun MobileDarkPreview() {
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
private fun TabletLightPreview() {
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
