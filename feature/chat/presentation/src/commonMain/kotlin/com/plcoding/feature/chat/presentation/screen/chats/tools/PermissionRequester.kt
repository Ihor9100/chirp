package com.plcoding.feature.chat.presentation.screen.chats.tools

import androidx.compose.runtime.Composable
import com.plcoding.feature.chat.presentation.permissions.Permission

interface PermissionRequester {
  @Composable
  fun requestPermission(permission: Permission)
}