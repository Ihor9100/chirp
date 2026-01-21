package com.plcoding.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalNavResult = staticCompositionLocalOf<NavResult> {
  error("NavResult is not provided")
}

@Composable
fun rememberNavResult(): NavResult {
  return remember { NavResult() }
}

class NavResult {

  private val results: MutableMap<String, Any> = mutableMapOf()

  fun setResult(key: String, result: Any) {
    results[key] = result
  }

  @Suppress("UNCHECKED_CAST")
  fun <T> getResult(key: String): T? {
    val result = results[key] as? T
    results.remove(key)
    return result
  }
}
