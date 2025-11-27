package com.plcoding.core.domain.validation

object PasswordValidator : InputValidator {

  override fun validate(input: String): Boolean {
    return input.length >= 9 &&
      input.any { it.isDigit() } &&
      input.any { it.isUpperCase() }
  }
}