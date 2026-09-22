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
  selectionLimit: Int,
  maxSizeBytes: Long?,
  onResult: (List<ImagePickerResult>) -> Unit,
): ImagePickerLauncher {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  val singleLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri ->
    coroutineScope.launch {
      uri ?: return@launch

      val contentUriParser = ContentUriParser(context)
      onResult(listOf(contentUriParser.parseImage(uri, maxSizeBytes)))
    }
  }

  val multipleLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = selectionLimit.coerceAtLeast(2))
  ) { uris ->
    coroutineScope.launch {
      val contentUriParser = ContentUriParser(context)
      val results = uris.map { uri ->
        contentUriParser.parseImage(uri, maxSizeBytes)
      }

      onResult(results)
    }
  }

  return remember {
    ImagePickerLauncher {
      val filter = ActivityResultContracts.PickVisualMedia.ImageOnly
      if (selectionLimit <= 1) {
        singleLauncher.launch(PickVisualMediaRequest(filter))
      } else {
        multipleLauncher.launch(PickVisualMediaRequest(filter))
      }
    }
  }
}
