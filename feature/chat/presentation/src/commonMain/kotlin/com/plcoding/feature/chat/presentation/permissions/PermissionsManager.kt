package com.plcoding.feature.chat.presentation.permissions

expect class PermissionsManager {
  suspend fun requestPermission(permission: Permission): PermissionState
}