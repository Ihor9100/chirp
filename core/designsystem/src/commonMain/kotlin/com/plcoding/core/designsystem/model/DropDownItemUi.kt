@file:OptIn(ExperimentalUuidApi::class)

package com.plcoding.core.designsystem.model

import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.failed
import chirp.core.designsystem.generated.resources.hide_password
import chirp.core.designsystem.generated.resources.ic_settings
import chirp.core.designsystem.generated.resources.show_password
import com.plcoding.core.designsystem.style.ColorToken
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class DropDownItemUi(
  val id: String,
  val leadingIconRes: DrawableResource?,
  val titleRes: StringResource,
  val colorToken: ColorToken,
) {

  companion object {
    val mocks
      get() = listOf(
        DropDownItemUi(
          id = Uuid.random().toString(),
          leadingIconRes = Res.drawable.ic_settings,
          titleRes = Res.string.hide_password,
          colorToken = ColorToken.TextSecondary,
        ),
        DropDownItemUi(
          id = Uuid.random().toString(),
          leadingIconRes = Res.drawable.ic_settings,
          titleRes = Res.string.show_password,
          colorToken = ColorToken.TextSecondary,
        ),
        DropDownItemUi(
          id = Uuid.random().toString(),
          leadingIconRes = Res.drawable.ic_settings,
          titleRes = Res.string.failed,
          colorToken = ColorToken.TextDestructive,
        ),
      )
  }
}
