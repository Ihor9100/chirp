package com.plcoding.chirp

import androidx.compose.ui.window.ComposeUIViewController
import com.plcoding.chirp.screen.app.AppScreen

fun MainViewController() = ComposeUIViewController { AppScreen() }