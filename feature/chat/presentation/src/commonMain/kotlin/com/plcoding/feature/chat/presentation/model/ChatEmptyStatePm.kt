package com.plcoding.feature.chat.presentation.model

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import org.jetbrains.compose.resources.StringResource

data class ChatEmptyStatePm(
  val titleRes: StringResource,
  val descriptionRes: StringResource,
) {

  companion object {
    val mock get() = ChatEmptyStatePm(
      titleRes = Res.string.no_messages,
      descriptionRes = Res.string.no_messages_subtitle,
    )
  }
}
