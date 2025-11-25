package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
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
import com.plcoding.core.designsystem.components.brand.ChirpBrandLogo
import com.plcoding.core.designsystem.components.brand.ChirBrandTitle
import com.plcoding.core.designsystem.style.ChirpTheme
import kotlinx.serialization.json.JsonNull.content
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpLayout(
  modifier: Modifier = Modifier,
  contentColumnTopSpaceDp: Dp = 16.dp,
  logo: @Composable () -> Unit,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(MaterialTheme.colorScheme.background),
  ) {
    Spacer(Modifier.height(32.dp))
    logo()
    Spacer(Modifier.height(32.dp))
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
    }
  }
}

@Composable
fun ChirpLayoutThemed(
  isDarkTheme: Boolean,
) {
  ChirpTheme(isDarkTheme) {
    ChirpLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { ChirpBrandLogo(modifier = Modifier) },
      content = {
        ChirBrandTitle(
          title = "Welcome to Chirp!",
          error = "Error"
        )
      }
    )
  }
}

@Composable
@Preview
fun ChirpLayoutLightPreview() {
  ChirpLayoutThemed(isDarkTheme = false)
}

@Composable
@Preview
fun ChirpLayoutDarkPreview() {
  ChirpLayoutThemed(isDarkTheme = true)
}
