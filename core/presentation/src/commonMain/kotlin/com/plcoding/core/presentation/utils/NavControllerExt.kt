package com.plcoding.core.presentation.utils

import androidx.navigation.NavController

fun NavController.navigateNewRoot(destination: Any) {
  navigate(destination) {
    popUpTo(graph.id) { inclusive = true }
  }
}
