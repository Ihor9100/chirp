package com.plcoding.core.domain.utils

sealed interface DataError : Error {
  enum class Remote : Error {

  }

  enum class Local : Error {

  }
}