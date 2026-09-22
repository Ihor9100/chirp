package com.plcoding.feature.chat.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.plcoding.feature.chat.database.entity.ChatMessageAttachmentEntity
import com.plcoding.feature.chat.database.view.ChatLastMessageView

data class ChatLastMessageAndAttachmentsRelation(
  @Embedded
  val chatLastMessageView: ChatLastMessageView,
  @Relation(
    parentColumn = "id",
    entityColumn = "messageId",
  )
  val attachmentEntities: List<ChatMessageAttachmentEntity>,
)
