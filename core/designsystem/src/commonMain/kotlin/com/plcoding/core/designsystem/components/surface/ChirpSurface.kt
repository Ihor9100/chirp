package com.plcoding.core.designsystem.components.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.brand.ChirpBrandLogo
import com.plcoding.core.designsystem.components.brand.ChirBrandTitle
import com.plcoding.core.designsystem.style.ChirTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpSurface(
  modifier: Modifier = Modifier,
  logo: @Composable () -> Unit,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(MaterialTheme.colorScheme.background),
  ) {
    logo()
    Column(
      modifier = Modifier
        .fillMaxSize()
        .clip(
          RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
          )
        )
        .background(MaterialTheme.colorScheme.surface),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      content()
    }
  }
}

@Composable
@Preview
fun ChirpSurfaceLightPreview() {
  ChirpSurfacePreview(isDarkTheme = false)
}

@Composable
@Preview
fun ChirpSurfaceDarkPreview() {
  ChirpSurfacePreview(isDarkTheme = true)
}

@Composable
fun ChirpSurfacePreview(
  isDarkTheme: Boolean,
) {
  ChirTheme(isDarkTheme) {
    ChirpSurface(
      modifier = Modifier
        .fillMaxSize(),
      logo = {
        ChirpBrandLogo(
          modifier = Modifier
            .padding(32.dp)
        )
      },
      content = {
        Spacer(Modifier.height(16.dp))
        ChirBrandTitle(
          title = "Welcome to Chirp!",
          error = "Error"
        )
      }
    )
  }
}
