package com.plcoding.core.domain.validator

object EmailValidator : InputValidator {

  private const val REGULAR_EXPRESSION = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"

  override fun validate(input: String): Boolean {
    return REGULAR_EXPRESSION.toRegex().matches(input)
  }
}