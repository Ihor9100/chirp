package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageAttachmentDto(
  val id: String,
  val messageId: String? = null,
  val url: String,
  val mimeType: String,
  val sizeBytes: Long,
  val type: String,
)
