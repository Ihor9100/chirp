package com.plcoding.core.designsystem.components.dialog

import androidx.compose.ui.window.DialogProperties

expect fun getDialogProperties(
  dismissOnBackPress: Boolean = true,
  dismissOnClickOutside: Boolean = true,
  usePlatformDefaultWidth: Boolean = true,
  usePlatformInsets: Boolean = true,
): DialogProperties
