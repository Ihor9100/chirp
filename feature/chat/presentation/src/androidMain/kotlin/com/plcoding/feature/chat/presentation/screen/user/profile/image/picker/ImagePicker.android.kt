package com.plcoding.feature.chat.presentation.screen.user.profile.image.picker

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
actual fun rememberImagePickerLauncher(
  onResult: (ImagePickerResult) -> Unit,
): ImagePickerLauncher {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri ->
    coroutineScope.launch {
      uri ?: return@launch

      val contentUriParser = ContentUriParser(context)
      val imagePickerResult = ImagePickerResult(
        contentUriParser.parseUri(uri),
        contentUriParser.getMimeType(uri),
      )

      onResult(imagePickerResult)
    }
  }

  return remember {
    ImagePickerLauncher {
      val filter = ActivityResultContracts.PickVisualMedia.ImageOnly
      launcher.launch(PickVisualMediaRequest(filter))
    }
  }
}
