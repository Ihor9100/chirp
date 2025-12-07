package com.plcoding.core.domain.logger

interface ChirpLogger {
  fun debug(message: String)
  fun error(message: String, throwable: Throwable? = null)
}