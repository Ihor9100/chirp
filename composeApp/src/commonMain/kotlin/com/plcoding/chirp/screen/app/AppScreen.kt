package com.plcoding.chirp.screen.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.plcoding.chirp.navigation.DeepLinksListener
import com.plcoding.chirp.navigation.NavigationRoot
import com.plcoding.core.designsystem.style.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun AppScreen(
  appViewModel: AppViewModel = koinViewModel(),
  removeSplashScreen: (() -> Unit)? = null,
) {
  val state by appViewModel.state.collectAsStateWithLifecycle()
  val startDestination = state.startDestination
  val navController = rememberNavController()

  LaunchedEffect(state.startDestination) {
    if (state.startDestination != null) removeSplashScreen?.invoke()
  }

  DeepLinksListener(navController)
  ChirpTheme {
    if (startDestination != null) {
      NavigationRoot(navController, startDestination)
    }
  }
}
