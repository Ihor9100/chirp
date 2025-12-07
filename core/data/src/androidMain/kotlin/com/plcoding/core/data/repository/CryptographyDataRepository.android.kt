package com.plcoding.core.data.repository

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.plcoding.core.domain.repository.CryptographyRepository
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptographyDataRepository : CryptographyRepository {

  companion object {
    const val SECRET_KEY_ALIES = "secret"
    const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
  }

  private val cipher = Cipher.getInstance(TRANSFORMATION)
  private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

  override fun encrypt(byteArray: ByteArray): ByteArray {
    cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
    val iv = cipher.iv
    val encrypted = cipher.doFinal(byteArray)
    return iv + encrypted
  }

  override fun decrypt(byteArray: ByteArray): ByteArray {
    val iv = byteArray.copyOfRange(0, cipher.blockSize)
    val encrypted = byteArray.copyOfRange(cipher.blockSize, byteArray.size)
    cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), IvParameterSpec(iv))
    return cipher.doFinal(encrypted)
  }

  private fun getSecretKey(): SecretKey {
    val entry = keyStore.getEntry(SECRET_KEY_ALIES, null)
    return (entry as? KeyStore.SecretKeyEntry)?.secretKey ?: createSecretKey()
  }

  private fun createSecretKey(): SecretKey {
    return KeyGenerator
      .getInstance(ALGORITHM)
      .apply {
        init(
          KeyGenParameterSpec.Builder(
            SECRET_KEY_ALIES,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
          )
            .setBlockModes(BLOCK_MODE)
            .setEncryptionPaddings(PADDING)
            .build()
        )
      }
      .generateKey()
  }
}
