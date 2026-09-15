package com.plcoding.feature.auth.presentation.screen.register.success

import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.account_successfully_created
import com.plcoding.core.presentation.model.TextProvider
import org.jetbrains.compose.resources.StringResource

data class RegisterSuccessUiState(
  val description: TextProvider? = null,
  val primaryButtonIsLoading: Boolean = false,
  val secondaryButtonErrorRes: StringResource? = null,
  val hasOngoingRequest: Boolean = false
)