package com.plcoding.core.presentation.event

import org.jetbrains.compose.resources.StringResource

data class SnackbarEvent(
  override val data: StringResource,
  override val onConsumed: () -> Unit,
) : Event<StringResource>