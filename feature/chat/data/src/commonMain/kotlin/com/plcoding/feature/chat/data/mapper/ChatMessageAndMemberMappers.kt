package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.database.relation.ChatMessageAndMemberRelation
import com.plcoding.feature.chat.domain.model.ChatMessageAndMember
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus

fun ChatMessageAndMemberRelation.toDomain(): ChatMessageAndMember = ChatMessageAndMember(
  chatMessage = chatMessageEntity.toDomain(),
  chatMember = chatMemberEntity.toDomain(),
  deliveryStatus = ChatMessageDeliveryStatus.SENT,
)
