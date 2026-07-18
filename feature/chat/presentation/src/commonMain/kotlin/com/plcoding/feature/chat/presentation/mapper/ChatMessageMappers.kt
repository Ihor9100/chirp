package com.plcoding.feature.chat.presentation.mapper

import com.plcoding.core.designsystem.model.AnchorPositionUi
import com.plcoding.core.designsystem.model.AvatarSizeUi
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.feature.chat.domain.model.ChatMessageAndMember
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.plcoding.feature.chat.presentation.model.ChatBoxUi
import com.plcoding.feature.chat.presentation.model.ChatMessageStatusUi
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import com.plcoding.feature.chat.presentation.utils.DateUtils
import com.plcoding.feature.chat.presentation.utils.FormatUtils.getInitials

fun ChatMessageAndMember.toUi(yourId: String?): ChatMessageUi {
  return if (chatMember.userId == yourId) {
    ChatMessageUi.LocalMessageUi(
      id = chatMessage.id,
      chatBoxUi = ChatBoxUi(
        anchorPositionUi = AnchorPositionUi.RIGHT,
        sender = chatMember.username,
        date = DateUtils.formatMessageTime(chatMessage.createdAt),
        message = chatMessage.content,
        colorToken = ColorToken.get(chatMember.userId),
      ),
      chatMessageStatusUi = when (chatMessage.deliveryStatus) {
        ChatMessageDeliveryStatus.SENDING,
        ChatMessageDeliveryStatus.SENT -> ChatMessageStatusUi.success
        ChatMessageDeliveryStatus.FAILED -> ChatMessageStatusUi.error
      },
      showRetryIcon = chatMessage.deliveryStatus == ChatMessageDeliveryStatus.FAILED,
    )
  } else {
    ChatMessageUi.RemoteMessageUi(
      id = chatMessage.id,
      avatarUi = AvatarUi(
        initials = getInitials(chatMember.username),
        imageUrl = chatMember.profilePictureUrl,
        avatarSizeUi = AvatarSizeUi.MEDIUM,
      ),
      chatBoxUi = ChatBoxUi(
        anchorPositionUi = AnchorPositionUi.LEFT,
        sender = chatMember.username,
        date = DateUtils.formatMessageTime(chatMessage.createdAt),
        message = chatMessage.content,
        colorToken = ColorToken.get(chatMember.userId),
      ),
    )
  }
}
