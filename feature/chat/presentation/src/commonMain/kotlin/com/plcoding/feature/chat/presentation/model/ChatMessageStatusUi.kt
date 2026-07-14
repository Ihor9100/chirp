package com.plcoding.feature.chat.presentation.model

import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.failed
import chirp.core.designsystem.generated.resources.ic_check
import chirp.core.designsystem.generated.resources.ic_cross
import chirp.core.designsystem.generated.resources.sent
import com.plcoding.core.designsystem.style.ColorToken
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@ConsistentCopyVisibility
data class ChatMessageStatusUi private constructor(
  val iconRes: DrawableResource,
  val titleRes: StringResource,
  val colorToken: ColorToken,
) {

  companion object {
    val success: ChatMessageStatusUi
      get() = ChatMessageStatusUi(
        iconRes = Res.drawable.ic_check,
        titleRes = Res.string.sent,
        colorToken = ColorToken.TextTertiary,
      )

    val error: ChatMessageStatusUi
      get() = ChatMessageStatusUi(
        iconRes = Res.drawable.ic_cross,
        titleRes = Res.string.failed,
        colorToken = ColorToken.Error,
      )
  }
}
