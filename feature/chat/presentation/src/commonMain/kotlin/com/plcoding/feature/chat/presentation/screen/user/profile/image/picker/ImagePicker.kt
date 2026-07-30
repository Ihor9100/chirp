package com.plcoding.feature.chat.presentation.screen.user.profile.image.picker

import androidx.compose.runtime.Composable

@Composable
expect fun rememberImagePickerLauncher(
  onResult: (ImagePickerResult) -> Unit,
): ImagePickerLauncher

class ImagePickerResult(
  val byteArray: ByteArray?,
  val mimeType: String?,
)

class ImagePickerLauncher(
  private val onLaunch: () -> Unit,
) {
  operator fun invoke() {
    onLaunch()
  }
}
