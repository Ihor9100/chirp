package com.plcoding.feature.chat.presentation.permissions

interface PermissionsManager {
  suspend fun requestPermission(permission: Permission): PermissionState
}