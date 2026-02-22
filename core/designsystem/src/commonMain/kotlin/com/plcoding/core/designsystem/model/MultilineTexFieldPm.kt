package com.plcoding.core.designsystem.model

import androidx.compose.foundation.text.input.TextFieldState
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.enter_your_message
import chirp.core.designsystem.generated.resources.sent
import org.jetbrains.compose.resources.StringResource

data class MultilineTextFieldPm(
  val textFieldState: TextFieldState,
  val inputPlaceholderRes: StringResource,
  val buttonTitleRes: StringResource,
  val isWideScreenMode: Boolean, // will be changed in screen with help of copy() method
  val isEnabled: Boolean,
) {

  companion object {
    val mock
      get() = MultilineTextFieldPm(
        textFieldState = TextFieldState(),
        inputPlaceholderRes = Res.string.enter_your_message,
        buttonTitleRes = Res.string.sent,
        isWideScreenMode = false,
        isEnabled = true,
      )
  }
}
