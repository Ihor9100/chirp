package com.plcoding.feature.chat.presentation.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.failed
import chirp.core.designsystem.generated.resources.ic_check
import chirp.core.designsystem.generated.resources.ic_cross
import chirp.core.designsystem.generated.resources.sent
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@ConsistentCopyVisibility
data class ChatSendingStatusPm private constructor(
  val iconRes: DrawableResource,
  val titleRes: StringResource,
  val colorToken: ColorToken,
) {

  companion object {
    val success: ChatSendingStatusPm
      get() = ChatSendingStatusPm(
        iconRes = Res.drawable.ic_check,
        titleRes = Res.string.sent,
        colorToken = ColorToken.TextTertiary,
      )

    val error: ChatSendingStatusPm
      get() = ChatSendingStatusPm(
        iconRes = Res.drawable.ic_cross,
        titleRes = Res.string.failed,
        colorToken = ColorToken.Error,
      )
  }
}
