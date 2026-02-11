package com.plcoding.core.designsystem.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.failed
import chirp.core.designsystem.generated.resources.ic_check
import chirp.core.designsystem.generated.resources.ic_cross
import chirp.core.designsystem.generated.resources.sent
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.vectorResource

@ConsistentCopyVisibility
data class ChatSendingStatusPm private constructor(
  val icon: ImageVector,
  val titleRes: StringResource,
  val color: Color,
) {

  companion object {
    val success: ChatSendingStatusPm
      @Composable
      get() = ChatSendingStatusPm(
        icon = vectorResource(Res.drawable.ic_check),
        titleRes = Res.string.sent,
        color = MaterialTheme.colorScheme.extended.textTertiary,
      )

    val error: ChatSendingStatusPm
      @Composable
      get() = ChatSendingStatusPm(
        icon = vectorResource(Res.drawable.ic_cross),
        titleRes = Res.string.failed,
        color = MaterialTheme.colorScheme.error,
      )
  }
}
