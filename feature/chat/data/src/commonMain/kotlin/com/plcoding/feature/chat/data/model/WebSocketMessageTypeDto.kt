package com.plcoding.feature.chat.data.model

enum class WebSocketMessageTypeDto {
  NEW_MESSAGE,
  MESSAGE_DELETED,
  PROFILE_PICTURE_UPDATED,
  CHAT_PARTICIPANTS_CHANGED;
}