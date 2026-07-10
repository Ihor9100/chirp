package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketMessageAm(
  val type: String,
  val payload: String,
)