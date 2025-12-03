package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import androidx.navigation.NavUri

@Composable
fun DeepLinksListener(navController: NavController) {
  DisposableEffect(Unit) {
    ExternalUriHandler.listener = {
      navController.navigate(NavUri(it))
    }

    onDispose {
      ExternalUriHandler.listener = null
    }
  }
}