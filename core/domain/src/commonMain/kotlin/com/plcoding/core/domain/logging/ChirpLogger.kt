package com.plcoding.core.domain.logging

interface ChirpLogger {
  fun debug(message: String)
  fun error(message: String, throwable: Throwable? = null)
}
