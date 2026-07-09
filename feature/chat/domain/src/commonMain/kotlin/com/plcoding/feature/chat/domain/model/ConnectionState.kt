package com.plcoding.feature.chat.domain.model

enum class ConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    ERROR_NETWORK,
    ERROR_UNKNOWN;
}