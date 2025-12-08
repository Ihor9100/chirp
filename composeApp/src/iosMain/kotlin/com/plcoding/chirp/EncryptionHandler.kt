package com.plcoding.chirp

object EncryptionHandler {
  var encryptionCallback: ((String) -> String)? = null
  var decryptionCallback: ((String) -> String)? = null
  fun encrypt(callback: (String) -> String) {
    encryptionCallback = callback
  }
  fun decrypt(callback: (String) -> String) {
    decryptionCallback = callback
  }
}