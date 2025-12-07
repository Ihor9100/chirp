package com.plcoding.core.data.logger

import co.touchlab.kermit.Logger
import com.plcoding.core.domain.logger.ChirpLogger
import com.plcoding.core.domain.repository.CryptographyRepository

object KermitLogger: ChirpLogger {

  override fun debug(message: String) {
    Logger.d(message)
    CryptographyRepository
  }

  override fun error(message: String, throwable: Throwable?) {
    Logger.e(message, throwable)
  }
}