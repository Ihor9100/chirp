package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatMessageAm
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatMessageAndMemberRelation
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageAndMember
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus

class ChatMessageAndMemberMapper(
  private val chatMessageEntityMapper: ChatMessageEntityMapper,
  private val chatMemberEntityMapper: ChatMemberEntityMapper,
) : Mapper<ChatMessageAndMemberRelation, ChatMessageAndMember> {

  override fun map(from: ChatMessageAndMemberRelation): ChatMessageAndMember {
    return with(from) {
      ChatMessageAndMember(
        chatMessage = chatMessageEntityMapper.reverse(chatMessageEntity),
        chatMember = chatMemberEntityMapper.reverse(from.chatMemberEntity),
        // TODO:  
        deliveryStatus = ChatMessageDeliveryStatus.SENT,
      )
    }
  }
}
