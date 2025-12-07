package com.plcoding.chirp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.core.domain.repository.CryptographyRepository

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    setContent {
      CryptographyRepository
      Column(
        modifier = Modifier
          .fillMaxSize()
          .safeDrawingPadding()
          .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          "Hello World",
          Modifier.size(50.dp),
        )
      }
    }
  }
}

@Preview
@Composable
fun AppAndroidPreview() {
  App()
}