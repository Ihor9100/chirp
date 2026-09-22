package com.plcoding.feature.chat.presentation.screen.user.profile.image.picker

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentUriParser(
  private val context: Context,
) {

  suspend fun parseImage(
    uri: Uri,
    maxSizeBytes: Long?,
  ): ImagePickerResult {
    val mimeType = getMimeType(uri)
    val knownSize = getSizeBytes(uri)

    if (maxSizeBytes != null && knownSize != null && knownSize > maxSizeBytes) {
      return ImagePickerResult(
        byteArray = null,
        mimeType = mimeType,
        isTooLarge = true,
      )
    }

    val bytes = parseUri(uri, maxSizeBytes)

    return ImagePickerResult(
      byteArray = bytes?.takeIf { maxSizeBytes == null || it.size <= maxSizeBytes },
      mimeType = mimeType,
      isTooLarge = bytes != null && maxSizeBytes != null && bytes.size > maxSizeBytes,
    )
  }

  private suspend fun parseUri(
    uri: Uri,
    maxSizeBytes: Long?,
  ): ByteArray? {
    return withContext(Dispatchers.IO) {
      context.contentResolver.openInputStream(uri).use {
        if (it == null) return@withContext null

        val output = java.io.ByteArrayOutputStream()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var totalBytes = 0L

        while (true) {
          val read = it.read(buffer)
          if (read == -1) break

          totalBytes += read
          output.write(buffer, 0, read)

          if (maxSizeBytes != null && totalBytes > maxSizeBytes) {
            break
          }
        }

        output.toByteArray()
      }
    }
  }

  fun getMimeType(uri: Uri): String? {
    return context.contentResolver.getType(uri)
      ?: getMimeTypeFromExtension(uri)
  }

  private fun getMimeTypeFromExtension(uri: Uri): String? {
    // .jpeg | .pdf | .txt
    val extension = uri.toString().substringAfterLast(".", "")
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
  }

  private fun getSizeBytes(uri: Uri): Long? {
    return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
      val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
      if (sizeIndex == -1 || !cursor.moveToFirst()) return@use null
      cursor.getLong(sizeIndex).takeIf { it >= 0 }
    }
  }
}
