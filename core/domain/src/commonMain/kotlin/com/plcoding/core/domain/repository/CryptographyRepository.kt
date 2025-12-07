package com.plcoding.core.domain.repository

interface CryptographyRepository {
  fun encrypt(byteArray: ByteArray): ByteArray
  fun decrypt(byteArray: ByteArray): ByteArray
}
