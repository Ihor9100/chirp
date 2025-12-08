package com.plcoding.core.data.repository

import com.plcoding.core.domain.repository.CryptographyRepository

class CryptographyDataRepository : CryptographyRepository {

  override fun encrypt(string: String): String {
    return string
  }

  override fun decrypt(string: String): String {
    return string
  }
}
