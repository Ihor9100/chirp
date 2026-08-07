package com.plcoding.feature.chat.presentation.permissions

import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import dev.icerock.moko.permissions.Permission as MokoPermission

actual class PermissionsManager(
  private val permissionsController: PermissionsController,
) {

  actual suspend fun requestPermission(permission: Permission): PermissionState {
    return try {
      permissionsController.providePermission(permission.toMokoPermission())
      PermissionState.GRANTED
    } catch (_: DeniedAlwaysException) {
      PermissionState.DENIED_ALWAYS
    } catch (_: DeniedException) {
      PermissionState.DENIED
    } catch (_: RequestCanceledException) {
      PermissionState.DENIED
    }
  }

  private fun Permission.toMokoPermission(): MokoPermission {
    return when (this) {
      Permission.NOTIFICATIONS -> MokoPermission.REMOTE_NOTIFICATION
    }
  }
}