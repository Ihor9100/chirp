package com.plcoding.feature.chat.domain.model

import com.plcoding.core.domain.result.Error

enum class ConnectionError: Error {
    NOT_CONNECTED,
    MESSAGE_SEND_FAILED
}