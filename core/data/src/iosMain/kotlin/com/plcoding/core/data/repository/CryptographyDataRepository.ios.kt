package com.plcoding.core.data.repository

import com.plcoding.core.domain.repository.CryptographyRepository

class CryptographyDataRepository : CryptographyRepository {

  companion object {
    var iosEncrypt: ((String) -> String)? = null
    var iosDecrypt: ((String) -> String)? = null
  }

  override fun encrypt(string: String): String {
    return string
  }

  override fun decrypt(string: String): String {
    return string
  }
}
