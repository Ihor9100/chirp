package com.plcoding.core.designsystem.components.surface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_logo_chirp
import com.plcoding.core.designsystem.style.ChirTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpSurface(
  modifier: Modifier = Modifier,
  logo: @Composable () -> Unit,
  content: @Composable ColumnScope.() -> Unit,
) {
  Surface(
    modifier = modifier,
    color = MaterialTheme.colorScheme.background,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxSize(),
    ) {
      logo()
      Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(
          topStart = 20.dp,
          topEnd = 20.dp,
        ),
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
      ) {
        Column(
          modifier = Modifier.fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          content()
        }
      }
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
        Icon(
          imageVector = vectorResource(Res.drawable.ic_logo_chirp),
          contentDescription = null,
          modifier = Modifier.padding(32.dp),
          tint = MaterialTheme.colorScheme.primary
        )
      },
      content = {
        Text(
          text = "Welcome to Chirp!",
          modifier = Modifier
            .padding(40.dp),
          style = MaterialTheme.typography.headlineLarge,
          color = MaterialTheme.colorScheme.extended.textPrimary
        )
      }
    )
  }
}
