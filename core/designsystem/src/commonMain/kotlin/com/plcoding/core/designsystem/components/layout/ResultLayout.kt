package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.SuccessIcon
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ResultLayout(
  modifier: Modifier = Modifier,
  icon: @Composable () -> Unit,
  title: String,
  description: String?,
  contentOffset: Dp = (-24).dp,
  primaryButton: @Composable () -> Unit,
  secondaryButton: @Composable (() -> Unit)?,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    icon()
    Column(
      modifier = Modifier
        .offset(y = contentOffset),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = title,
        color = MaterialTheme.colorScheme.extended.textPrimary,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
      )
      description?.let {
        Spacer(Modifier.height(8.dp))
        Text(
          text = description,
          color = MaterialTheme.colorScheme.extended.textSecondary,
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.bodySmall,
        )
      }
      Spacer(Modifier.height(32.dp))
      primaryButton()
      if (secondaryButton != null) {
        secondaryButton()
      }
    }
  }
}

@Composable
fun ResultLayoutThemed(
  isDarkTheme: Boolean,
) {
  Theme(isDarkTheme) {
    ResultLayoutMock()
  }
}

@Composable
fun ResultLayoutMock() {
  ResultLayout(
    icon = {
      SuccessIcon()
    },
    title = "Chirp successfully created!",
    description = "We’ve sent verification email to olivia@chirp.chat",
    primaryButton = {
      ButtonPc(
        modifier = Modifier.fillMaxWidth(),
        text = "Log in",
      )
    },
    secondaryButton = {
      ButtonPc(
        modifier = Modifier.fillMaxWidth(),
        text = "Resend verification email",
        style = ButtonPcStyle.SECONDARY,
      )
    },
  )
}

@Composable
@Preview
fun ResultLayoutLightPreview() {
  ResultLayoutThemed(
    isDarkTheme = false,
  )
}

@Composable
@Preview
fun ResultLayoutDarkPreview() {
  ResultLayoutThemed(
    isDarkTheme = true,
  )
}
