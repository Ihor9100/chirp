package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.hide_password
import chirp.core.designsystem.generated.resources.ic_settings
import chirp.core.presentation.generated.resources.chirp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.ic_log_out
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.profile_settings
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.AvatarPc
import com.plcoding.core.designsystem.components.DropDownMenuPc
import com.plcoding.core.designsystem.components.HorizontalDividerPc
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.model.DropDownItemPm
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import chirp.core.designsystem.generated.resources.Res as DesignSystemRes
import chirp.core.presentation.generated.resources.Res as CorePresentationRes

@Composable
fun ChatsHeaderPc(
  modifier: Modifier = Modifier,
  showMenu: Boolean,
  avatarPm: AvatarPm,
  onAvatarClick: () -> Unit,
  onSettingsClick: () -> Unit,
  onLogoutClick: () -> Unit,
  onDismissClick: () -> Unit,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      AppLogoPc()
      Text(
        modifier = Modifier.weight(1f),
        text = stringResource(CorePresentationRes.string.chirp),
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleMedium,
      )
      AvatarPc(
        avatarPm = avatarPm,
        onClick = onAvatarClick,
      )
      DropDownMenuPc(
        modifier = Modifier,
        showMenu = showMenu,
        items = listOf(
          DropDownItemPm(
            leadingIconRes = DesignSystemRes.drawable.ic_settings,
            titleRes = Res.string.profile_settings,
            color = MaterialTheme.colorScheme.extended.textPrimary,
            onClick = { },
          ),
          DropDownItemPm(
            leadingIconRes = Res.drawable.ic_log_out,
            titleRes = Res.string.log_out,
            color = MaterialTheme.colorScheme.error,
            onClick = { },
          ),
        ),
        onDismiss = onDismissClick,
      )
    }
    HorizontalDividerPc()
  }
}

@Composable
private fun Themed(
  isDarkMode: Boolean,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    ChatsHeaderPc(
      avatarPm = AvatarPm.mocks[0],
      showMenu = true,
      onAvatarClick = {},
      onSettingsClick = {},
      onLogoutClick = {},
      onDismissClick = {},
    )
  }
}

@Composable
@Preview(heightDp = 250)
private fun DarkPreview() {
  Themed(
    isDarkMode = true,
  )
}

@Composable
@Preview(heightDp = 250)
private fun LightPreview() {
  Themed(
    isDarkMode = false,
  )
}
