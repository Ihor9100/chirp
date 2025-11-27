package com.plcoding.core.domain.validation

interface InputValidator {
  fun validate(input: String): Boolean
}