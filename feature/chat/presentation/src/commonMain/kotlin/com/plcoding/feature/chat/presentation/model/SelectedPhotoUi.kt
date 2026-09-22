package com.plcoding.feature.chat.presentation.model

data class SelectedPhotoUi(
  val id: String,
  val byteArray: ByteArray,
  val mimeType: String,
  val sizeBytes: Long,
)
