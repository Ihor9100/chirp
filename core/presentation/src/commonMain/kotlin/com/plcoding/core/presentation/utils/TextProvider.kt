package com.plcoding.core.presentation.utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

sealed interface TextProvider {
  data class Dynamic(val value: String) : TextProvider
  data class Resource(val id: StringResource, val args: List<Any>) : TextProvider

  @Composable
  fun get(): String {
    return when (this) {
      is Dynamic -> value
      is Resource -> stringResource(id, *args.toTypedArray())
    }
  }

  suspend fun getAsync(): String {
    return when (this) {
      is Dynamic -> value
      is Resource -> getString(id)
    }
  }
}
