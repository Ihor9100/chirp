package com.plcoding.chirp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.plcoding.chirp.screen.app.AppScreen

class MainActivity : ComponentActivity() {

  private var keepSplashScreen = true

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen().setKeepOnScreenCondition { keepSplashScreen }
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    setContent {
      AppScreen(removeSplashScreen = { keepSplashScreen = false })
    }
  }
}

@Preview
@Composable
fun AppScreenAndroidPreview() {
  AppScreen()
}