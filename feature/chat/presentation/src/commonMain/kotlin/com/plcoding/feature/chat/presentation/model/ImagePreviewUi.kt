package com.plcoding.feature.chat.presentation.model

sealed interface ImagePreviewUi {
  data class Local(val byteArray: ByteArray) : ImagePreviewUi
  data class Remote(val url: String) : ImagePreviewUi
}
