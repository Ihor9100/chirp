package com.plcoding.core.presentation.event

data class SimpleEvent(
  override val data: Unit = Unit,
  override val onConsumed: () -> Unit,
) : Event<Unit>
