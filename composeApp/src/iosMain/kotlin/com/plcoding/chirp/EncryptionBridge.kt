package com.plcoding.chirp

import com.plcoding.core.data.repository.CryptographyDataRepository

object EncryptionBridge {
  fun addEncrypt(encrypt: (String) -> String) {
    CryptographyDataRepository.nativeEncrypt = encrypt
  }

  fun addDecrypt(decrypt: (String) -> String) {
    CryptographyDataRepository.nativeDecrypt = decrypt
  }
}
