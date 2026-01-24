package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.Title
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FormLayout(
  modifier: Modifier = Modifier,
  contentColumnTopSpaceDp: Dp = 40.dp,
  logo: @Composable (() -> Unit)? = null,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier,
  ) {
    if (logo != null) {
      Spacer(Modifier.height(32.dp))
      logo()
      Spacer(Modifier.height(32.dp))
    }
    Column(
      modifier = Modifier
        .fillMaxSize()
        .clip(
          RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
          )
        )
        .background(MaterialTheme.colorScheme.surface)
        .padding(horizontal = 16.dp)
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Spacer(Modifier.height(contentColumnTopSpaceDp))
      content()
      Spacer(Modifier.height(32.dp))
    }
  }
}

@Composable
fun FormLayoutThemed(
  isDarkTheme: Boolean,
) {
  Theme(isDarkTheme) {
    FormLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { AppLogoPc(modifier = Modifier) },
      content = {
        Title(
          text = "Welcome to Chirp!",
          error = "Error"
        )
      }
    )
  }
}

@Composable
@Preview
fun FormLayoutLightPreview() {
  FormLayoutThemed(isDarkTheme = false)
}

@Composable
@Preview
fun FormLayoutDarkPreview() {
  FormLayoutThemed(isDarkTheme = true)
}
