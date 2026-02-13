package com.plcoding.core.designsystem.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.failed
import chirp.core.designsystem.generated.resources.hide_password
import chirp.core.designsystem.generated.resources.ic_settings
import chirp.core.designsystem.generated.resources.show_password
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class DropDownItemPm(
  val leadingIconRes: DrawableResource?,
  val titleRes: StringResource,
  val color: Color,
  val onClick: () -> Unit,
) {

  companion object {
    val mocks
      @Composable
      get() = listOf(
        DropDownItemPm(
          leadingIconRes = Res.drawable.ic_settings,
          titleRes = Res.string.hide_password,
          color = MaterialTheme.colorScheme.extended.textPrimary,
          onClick = { },
        ),
        DropDownItemPm(
          leadingIconRes = Res.drawable.ic_settings,
          titleRes = Res.string.show_password,
          color = MaterialTheme.colorScheme.extended.textPrimary,
          onClick = { },
        ),
        DropDownItemPm(
          leadingIconRes = Res.drawable.ic_settings,
          titleRes = Res.string.failed,
          color = MaterialTheme.colorScheme.error,
          onClick = { },
        ),
      )
  }
}
