package com.plcoding.core.domain.utils

sealed interface DataError : Error {
  enum class Remote : Error {
    SERIALIZATION,
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    REQUEST_TIMEOUT,
    CONFLICT,
    PAYLOAD_TOO_LARGE,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERVER_UNAVAILABLE,
    UNKNOWN,
  }

  enum class Local : Error {

  }
}