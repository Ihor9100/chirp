package com.plcoding.feature.chat.domain.model

data class ChatMessageAttachment(
  val id: String,
  val messageId: String,
  val url: String,
  val mimeType: String,
  val sizeBytes: Long,
  val type: ChatMessageAttachmentType,
)
