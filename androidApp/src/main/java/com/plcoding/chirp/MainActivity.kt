package com.plcoding.chirp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.plcoding.chirp.navigation.ExternalUriHandler
import com.plcoding.chirp.screen.app.AppScreen
import com.plcoding.core.domain.logger.Logger
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject
import kotlin.math.log

class MainActivity : ComponentActivity() {

  private var keepSplashScreen = true
  private val logger by inject<Logger>()

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen().setKeepOnScreenCondition { keepSplashScreen }
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    handleMessageNotificationClick(intent)
    setContent {
      AppScreen(removeSplashScreen = { keepSplashScreen = false })
    }
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    handleMessageNotificationClick(intent)
  }

  private fun handleMessageNotificationClick(intent: Intent) {
    val chatId = intent.getStringExtra("chatId")
    logger.debug("handleMessageNotificationClick | chatId: $chatId")

    if (chatId != null) {
      ExternalUriHandler.onNewUri("chirp://chat-details/$chatId")
    }
  }
}
