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
import chirp.core.designsystem.generated.resources.ic_settings
import chirp.core.presentation.generated.resources.Res
import chirp.core.designsystem.generated.resources.Res as DesignSystemRes
import chirp.core.presentation.generated.resources.chirp
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.profile_settings
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.AvatarPc
import com.plcoding.core.designsystem.components.HorizontalDividerPc
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 16.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp),
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
        text = stringResource(Res.string.chirp),
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleMedium,
      )
      Box {
        AvatarPc(
          modifier = Modifier,
          avatarPm = avatarPm,
        )
        DropdownMenu(
          expanded = showMenu,
          onDismissRequest = onDismissClick,
          shape = RoundedCornerShape(16.dp),
          containerColor = MaterialTheme.colorScheme.surface,
          shadowElevation = 0.dp,
          border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.extended.surfaceOutline,
          ),
        ) {
          DropdownMenuItem(
            onClick = onSettingsClick,
            colors = MenuDefaults.itemColors().copy(
              textColor = MaterialTheme.colorScheme.extended.textSecondary,
              leadingIconColor = MaterialTheme.colorScheme.extended.textSecondary,
            ),
            text = {
              Text(
                text = stringResource(Res.string.profile_settings),
                style = MaterialTheme.typography.labelMedium,
              )
            },
            leadingIcon = {
              Icon(
                modifier = Modifier.size(16.dp),
                imageVector = vectorResource(DesignSystemRes.drawable.ic_settings),
                contentDescription = null,
              )
            },
          )
          HorizontalDividerPc()
          DropdownMenuItem(
            onClick = onSettingsClick,
            colors = MenuDefaults.itemColors().copy(
              textColor = MaterialTheme.colorScheme.extended.destructiveHover,
              leadingIconColor = MaterialTheme.colorScheme.extended.destructiveHover,
            ),
            text = {
              Text(
                text = stringResource(Res.string.log_out),
                style = MaterialTheme.typography.labelMedium,
              )
            },
            leadingIcon = {
              Icon(
                modifier = Modifier.size(16.dp),
                imageVector = vectorResource(DesignSystemRes.drawable.ic_settings),
                contentDescription = null,
              )
            },
          )
        }
      }
    }
    HorizontalDividerPc()
  }
}

@Composable
private fun AvatarThemed(
  isDarkMode: Boolean,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    ChatsHeaderPc(
      avatarPm = AvatarPm.mock,
      showMenu = true,
      onAvatarClick = {},
      onSettingsClick = {},
      onLogoutClick = {},
      onDismissClick = {},
    )
  }
}

@Composable
@Preview
private fun DarkPreview() {
  AvatarThemed(
    isDarkMode = true,
  )
}

@Composable
@Preview
private fun LightPreview() {
  AvatarThemed(
    isDarkMode = false,
  )
}
