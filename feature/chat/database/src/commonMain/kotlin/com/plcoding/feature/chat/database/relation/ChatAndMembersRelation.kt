package com.plcoding.feature.chat.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.view.ChatLastMessageView

data class ChatAndMembersRelation(
  @Embedded
  val chatEntity: ChatEntity,
  @Relation(
    parentColumn = "id",
    entityColumn = "id",
    associateBy = Junction(
      value = ChatAndMemberEntity::class,
      parentColumn = "chatId",
      entityColumn = "memberId",
    ),
  )
  val chatMemberEntities: List<ChatMemberEntity>,
  @Relation(
    parentColumn = "id",
    entityColumn = "chatId",
    entity = ChatLastMessageView::class,
  )
  val chatLastMessageView: ChatLastMessageView,
)
