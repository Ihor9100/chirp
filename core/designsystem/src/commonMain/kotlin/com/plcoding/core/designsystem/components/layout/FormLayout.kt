package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.AppLogo
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
      Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
      Spacer(Modifier.height(32.dp))
      logo()
      Spacer(Modifier.height(32.dp))
    }
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(
          color = MaterialTheme.colorScheme.surface,
          shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        )
        .verticalScroll(rememberScrollState())
        .navigationBarsPadding()
        .imePadding()
        .padding(horizontal = 16.dp),
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
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
      logo = { AppLogo(modifier = Modifier) },
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
