package com.plcoding.core.presentation.utils.event

import org.jetbrains.compose.resources.StringResource

data class SnackbarEvent(
  override val data: StringResource,
  override val onConsumed: () -> Unit,
) : Event<StringResource>