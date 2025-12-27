package com.plcoding.chirp.screen.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.plcoding.chirp.navigation.DeepLinksListener
import com.plcoding.chirp.navigation.NavigationRoot
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.utils.navigateNewRoot
import com.plcoding.feature.auth.presentation.navigation.AuthRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun AppScreen(
  appViewModel: AppScreenViewModel = koinViewModel(),
  removeSplashScreen: (() -> Unit)? = null,
) {
  val state by appViewModel.state.collectAsStateWithLifecycle()
  val startDestination = state.content.startDestination
  val navController = rememberNavController()

  state.content.removeSplashScreenEvent?.run {
    removeSplashScreen?.invoke()
  }
  state.content.logoutEvent?.run {
    navController.navigateNewRoot(AuthRoute.Graph)
  }

  DeepLinksListener(navController)
  Theme {
    if (startDestination != null) {
      NavigationRoot(navController, startDestination)
    }
  }
}
