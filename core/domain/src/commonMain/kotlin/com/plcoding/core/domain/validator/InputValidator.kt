package com.plcoding.core.domain.validator

interface InputValidator {
  fun validate(input: String): Boolean
}