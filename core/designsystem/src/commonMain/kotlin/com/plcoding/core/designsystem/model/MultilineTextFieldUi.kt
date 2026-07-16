package com.plcoding.core.designsystem.model

import androidx.compose.foundation.text.input.TextFieldState
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.enter_your_message
import chirp.core.designsystem.generated.resources.ic_cloud_off
import chirp.core.designsystem.generated.resources.sent
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class MultilineTextFieldUi(
  val textFieldState: TextFieldState,
  val inputPlaceholderRes: StringResource,
  val buttonTitleRes: StringResource,
  val connectionIconRes: DrawableResource?,
  val isButtonEnabled: Boolean,
) {

  companion object {
    val mock
      get() = MultilineTextFieldUi(
        textFieldState = TextFieldState(),
        inputPlaceholderRes = Res.string.enter_your_message,
        buttonTitleRes = Res.string.sent,
        connectionIconRes = Res.drawable.ic_cloud_off,
        isButtonEnabled = false,
      )
  }
}
