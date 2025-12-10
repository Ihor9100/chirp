package com.plcoding.core.presentation.utils

import androidx.navigation.NavController

fun NavController.navigateNewRoot(destination: Any) {
  navigate(destination) {
    popUpTo(graph.id) { inclusive = true }
  }
}

fun NavController.navigateFresh(destination: Any) {
  navigate(destination) {
    popUpTo(destination) { inclusive = true }
  }
}

fun NavController.navigateWithPopUpTo(destination: Any, popUpTo: Any) {
  navigate(destination) {
    popUpTo(popUpTo) { inclusive = true }
  }
}
