package com.plcoding.core.data.logger

import co.touchlab.kermit.Logger
import com.plcoding.core.domain.logger.ChirpLogger

object KermitLogger: ChirpLogger {

  override fun debug(message: String) {
    Logger.d(message)
  }

  override fun error(message: String, throwable: Throwable?) {
    Logger.e(message, throwable)
  }
}