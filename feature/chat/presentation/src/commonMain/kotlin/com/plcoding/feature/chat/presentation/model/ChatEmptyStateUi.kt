package com.plcoding.feature.chat.presentation.model

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import org.jetbrains.compose.resources.StringResource

data class ChatEmptyStateUi(
  val titleRes: StringResource,
  val descriptionRes: StringResource,
) {

  companion object {
    val mock get() = ChatEmptyStateUi(
      titleRes = Res.string.no_messages,
      descriptionRes = Res.string.no_messages_subtitle,
    )
  }
}
