package com.plcoding.core.designsystem.components.dialog

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class)
actual fun getDialogProperties(
  dismissOnBackPress: Boolean,
  dismissOnClickOutside: Boolean,
  usePlatformDefaultWidth: Boolean,
  usePlatformInsets: Boolean,
): DialogProperties {
  return DialogProperties(
    dismissOnBackPress = dismissOnBackPress,
    dismissOnClickOutside = dismissOnClickOutside,
    usePlatformDefaultWidth = usePlatformDefaultWidth,
    usePlatformInsets = usePlatformInsets
  )
}
