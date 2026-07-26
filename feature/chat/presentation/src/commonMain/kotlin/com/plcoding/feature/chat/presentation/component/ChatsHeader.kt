package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.ic_settings
import chirp.core.presentation.generated.resources.chirp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.ic_log_out
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.profile_settings
import com.plcoding.core.designsystem.components.AppLogo
import com.plcoding.core.designsystem.components.Avatar
import com.plcoding.core.designsystem.components.DropDownMenu
import com.plcoding.core.designsystem.components.HorizontalDivider
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import chirp.core.presentation.generated.resources.Res as CoreRes
import chirp.core.designsystem.generated.resources.Res as DesignRes

@Composable
fun ChatsHeader(
  modifier: Modifier = Modifier,
  showDropDownMenu: Boolean,
  avatarUi: AvatarUi?,
  onAvatarClick: () -> Unit,
  onDropDownMenuItemClick: (DropDownItemUi) -> Unit,
  onDismiss: () -> Unit,
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
      AppLogo()
      Text(
        modifier = Modifier.weight(1f),
        text = stringResource(CoreRes.string.chirp),
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleMedium,
      )
      Box {
        if (avatarUi != null) {
          Avatar(
            avatarUi = avatarUi,
            onClick = onAvatarClick,
          )
        }
        if (showDropDownMenu) {
          DropDownMenu(
            modifier = Modifier,
            showMenu = true,
            items = listOf(
              DropDownItemUi(
                id = "1",
                leadingIconRes = DesignRes.drawable.ic_settings,
                titleRes = Res.string.profile_settings,
                colorToken = ColorToken.TextPrimary,
              ),
              DropDownItemUi(
                id = "2",
                leadingIconRes = Res.drawable.ic_log_out,
                titleRes = Res.string.log_out,
                colorToken = ColorToken.Error,
              ),
            ),
            onAction = onDropDownMenuItemClick,
            onDismiss = onDismiss,
          )
        }
      }
    }
    HorizontalDivider()
  }
}

@Composable
private fun Themed(
  isDarkMode: Boolean,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    ChatsHeader(
      avatarUi = AvatarUi.mocks[0],
      showDropDownMenu = true,
      onAvatarClick = {},
      onDropDownMenuItemClick = {},
      onDismiss = {},
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
