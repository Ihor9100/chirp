package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_eye_on
import com.plcoding.core.designsystem.style.ChirTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpSurface(
  modifier: Modifier = Modifier,
  header: @Composable ColumnScope.() -> Unit,
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
      header()
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
fun ChirpSurfacePreview() {
  ChirTheme {
    ChirpSurface(
      modifier = Modifier
        .fillMaxSize(),
      header = {
        Icon(
          imageVector = vectorResource(Res.drawable.ic_eye_on),
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
        )
      }
    )
  }
}