package com.plcoding.feature.chat.presentation.screen

data class ChatState(
  val paramOne: String = "default",
  val paramTwo: List<String> = emptyList(),
)