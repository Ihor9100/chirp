package com.plcoding.core.data.repository

import com.plcoding.core.domain.repository.CryptographyRepository

class CryptographyDataRepository : CryptographyRepository {

  companion object {
    var nativeEncrypt: ((String) -> String)? = null
    var nativeDecrypt: ((String) -> String)? = null
  }

  override fun encrypt(decrypted: String): String {
    return nativeEncrypt?.invoke(decrypted).orEmpty()
  }

  override fun decrypt(encrypted: String): String {
    return nativeDecrypt?.invoke(encrypted).orEmpty()
  }
}
