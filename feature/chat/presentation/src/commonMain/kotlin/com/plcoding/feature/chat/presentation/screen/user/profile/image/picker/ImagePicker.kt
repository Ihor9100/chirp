package com.plcoding.feature.chat.presentation.screen.user.profile.image.picker

import androidx.compose.runtime.Composable

@Composable
fun rememberImagePickerLauncher(
  onResult: (ImagePickerResult) -> Unit,
): ImagePickerLauncher {
  return rememberImagePickerLauncher(selectionLimit = 1, maxSizeBytes = null) { results ->
    results.firstOrNull()?.let(onResult)
  }
}

@Composable
expect fun rememberImagePickerLauncher(
  selectionLimit: Int,
  maxSizeBytes: Long? = null,
  onResult: (List<ImagePickerResult>) -> Unit,
): ImagePickerLauncher

class ImagePickerResult(
  val byteArray: ByteArray?,
  val mimeType: String?,
  val isTooLarge: Boolean = false,
)

class ImagePickerLauncher(
  private val onLaunch: () -> Unit,
) {
  operator fun invoke() {
    onLaunch()
  }
}
