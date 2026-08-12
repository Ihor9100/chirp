package com.plcoding.core.data.logger

import co.touchlab.kermit.Logger

object KermitLogger: com.plcoding.core.domain.logger.Logger {

  private const val TAG = "Chirp"

  override fun debug(message: String) {
    Logger.d(message, tag = TAG)
  }

  override fun error(message: String, throwable: Throwable?) {
    Logger.e(message, throwable, TAG)
  }
}