package com.plcoding.core.domain.validator

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UsernameValidatorTest {

  @Test
  fun `valid username returns true`() {
    assertTrue(UsernameValidator.validate("Ihor"))
  }

  @Test
  fun `invalid username returns false`() {
    assertFalse(UsernameValidator.validate("a".repeat(30)))
  }

  @Test
  fun `username with 3 chars returns true`() {
    assertTrue(UsernameValidator.validate("abc"))
  }

  @Test
  fun `username with 20 chars returns true`() {
    assertTrue(UsernameValidator.validate("a".repeat(20)))
  }

  @Test
  fun `username with 2 chars returns false`() {
    assertFalse(UsernameValidator.validate("ab"))
  }

  @Test
  fun `username with 21 chars returns false`() {
    assertFalse(UsernameValidator.validate("a".repeat(21)))
  }
}