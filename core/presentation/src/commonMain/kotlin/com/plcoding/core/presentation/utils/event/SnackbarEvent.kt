package com.plcoding.core.presentation.utils.event

data class SnackbarEvent(
  override val data: String,
  override val onConsumed: () -> Unit,
) : Event<String>