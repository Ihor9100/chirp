package com.plcoding.core.designsystem.components.dialog

import androidx.compose.ui.window.DialogProperties

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
    decorFitsSystemWindows = usePlatformInsets
  )
}
