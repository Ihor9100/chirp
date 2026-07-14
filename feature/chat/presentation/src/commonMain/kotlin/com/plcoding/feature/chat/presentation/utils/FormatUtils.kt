package com.plcoding.feature.chat.presentation.utils

object FormatUtils {

  fun getInitials(fullName: String): String {
    if (fullName.isBlank()) return "?"
    return fullName.split(" ").take(2).joinToString(separator = "") { it.first().uppercase() }
  }
}