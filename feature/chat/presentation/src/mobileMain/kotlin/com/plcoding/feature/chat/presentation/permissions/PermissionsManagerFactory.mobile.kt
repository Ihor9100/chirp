package com.plcoding.feature.chat.presentation.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

@Composable
actual fun rememberPermissionsManager(): PermissionsManager {
  val permissionsControllerFactory = rememberPermissionsControllerFactory()
  val permissionsController = remember {
    permissionsControllerFactory.createPermissionsController()
  }

  BindEffect(permissionsController)

  return remember {
    PermissionsManager(permissionsController)
  }
}