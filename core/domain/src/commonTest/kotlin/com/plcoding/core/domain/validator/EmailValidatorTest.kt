package com.plcoding.core.domain.validator

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmailValidatorTest {

  @Test
  fun `valid email returns true`() {
    assertTrue(EmailValidator.validate("ihor.bohdanovskyi@gmail.com"))
  }

  @Test
  fun `only local part returns false`() {
    assertFalse(EmailValidator.validate("ihor"))
  }

  @Test
  fun `local part empty returns false`() {
    assertFalse(EmailValidator.validate("@gmail.com"))
  }

  @Test
  fun `domain part empty returns false`() {
    assertFalse(EmailValidator.validate("ihor.bohdanovskyi@"))
  }

  @Test
  fun `at sign missed returns false`() {
    assertFalse(EmailValidator.validate("ihor.bohdanovskyi.gmail.com"))
  }

  @Test
  fun `one local part char returns true`() {
    assertTrue(EmailValidator.validate("i@gmail.com"))
  }

  @Test
  fun `one local part char and one domain char returns true`() {
    assertTrue(EmailValidator.validate("i@g"))
  }

  @Test
  fun `special char in local part returns true`() {
    assertTrue(EmailValidator.validate("+i@gmail.com"))
  }

  @Test
  fun `special char in domain part returns false`() {
    assertFalse(EmailValidator.validate("ihor@gma+il.com"))
  }
}