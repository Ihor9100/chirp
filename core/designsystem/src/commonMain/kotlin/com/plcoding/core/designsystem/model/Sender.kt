package com.plcoding.core.designsystem.model

sealed interface Sender {

  val name: String

  data class You(override val name: String) : Sender
  data class Other(override val name: String) : Sender
}
