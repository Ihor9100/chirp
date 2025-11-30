package com.plcoding.chirp

import androidx.compose.runtime.Composable
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.feature.auth.presentation.register.success.RegisterSuccessScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  ChirpTheme {
    RegisterSuccessScreen()
  }
}