package com.plcoding.core.domain.validator

object UsernameValidator: InputValidator {

  override fun validate(input: String): Boolean {
    return input.length in 3..20
  }
}