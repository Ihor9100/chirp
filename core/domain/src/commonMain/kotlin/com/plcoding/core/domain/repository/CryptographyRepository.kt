package com.plcoding.core.domain.repository

interface CryptographyRepository {
  fun encrypt(decrypted: String): String
  fun decrypt(encrypted: String): String
}
