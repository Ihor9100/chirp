package com.plcoding.core.domain.logger

interface Logger {
  fun debug(message: String)
  fun error(message: String, throwable: Throwable? = null)
}