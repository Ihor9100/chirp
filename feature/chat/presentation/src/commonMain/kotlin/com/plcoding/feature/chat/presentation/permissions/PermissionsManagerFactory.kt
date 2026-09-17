package com.plcoding.feature.chat.presentation.permissions

import androidx.compose.runtime.Composable

interface PermissionsManagerFactory {
  @Composable
  fun rememberPermissionsManager(): PermissionsManager
}
