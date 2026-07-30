package com.plcoding.feature.chat.presentation.screen.user.profile.image.picker

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentUriParser(
  private val context: Context,
) {

  suspend fun parseUri(uri: Uri): ByteArray? {
    return withContext(Dispatchers.IO) {
      context.contentResolver.openInputStream(uri).use {
        it?.readBytes()
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
}