package com.plcoding.core.domain.repository

interface CryptographyRepository {
  fun encrypt(string: String): String
  fun decrypt(string: String): String
}
