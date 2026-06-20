package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.database.relation.ChatAndMembersRelation
import com.plcoding.feature.chat.database.view.ChatLastMessageView
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.time.Instant

class ChatDetailsMapper(
  private val chatEntityMapper: ChatEntityMapper
): Mapper<ChatAndMembersAndMessagesRelation, ChatDetails, Unit> {

  override fun map(
    from: ChatAndMembersAndMessagesRelation,
    params: Unit
  ): ChatDetails {
    return with(from) {
      ChatDetails(
        chat =,
        chatMessageAndMembers =,
      )
    }
  }
}
