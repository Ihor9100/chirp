package com.plcoding.chirp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.plcoding.chirp.navigation.DeepLinksListener
import com.plcoding.chirp.navigation.NavigationRoot
import com.plcoding.core.designsystem.style.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  val navController = rememberNavController()
  DeepLinksListener(navController)
  ChirpTheme { NavigationRoot(navController) }
}