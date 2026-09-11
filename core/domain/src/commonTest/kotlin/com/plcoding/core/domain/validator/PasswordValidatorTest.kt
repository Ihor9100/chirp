package com.plcoding.core.domain.validator

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PasswordValidatorTest {

  @Test
  fun `valid password returns true`() {
    assertTrue(PasswordValidator.validate("Naruto!19890702"))
  }

  @Test
  fun `no digit returns false`() {
    assertFalse(PasswordValidator.validate("Narutoooo"))
  }

  @Test
  fun `no uppercase returns false`() {
    assertFalse(PasswordValidator.validate("naruto19890702"))
  }

  @Test
  fun `short password returns false`() {
    assertFalse(PasswordValidator.validate("Naruto1"))
  }

  @Test
  fun `9 chars returns true`() {
    assertTrue(PasswordValidator.validate("Naruto111"))
  }

  @Test
  fun `8 chars returns false`() {
    assertFalse(PasswordValidator.validate("Naruto11"))
  }
}