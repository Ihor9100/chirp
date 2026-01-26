package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.failed
import chirp.core.designsystem.generated.resources.show_password
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TitleDescriptionPc(
  modifier: Modifier = Modifier,
  titleRes: StringResource,
  descriptionRes: StringResource,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
  ) {
    Text(
      text = stringResource(titleRes),
      color = MaterialTheme.colorScheme.extended.textPrimary,
      style = MaterialTheme.typography.titleMedium,
    )
    Text(
      text = stringResource(descriptionRes),
      color = MaterialTheme.colorScheme.extended.textSecondary,
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@Composable
private fun Themed(
  isDarkMode: Boolean,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    TitleDescriptionPc(
      modifier = Modifier,
      titleRes = Res.string.failed,
      descriptionRes = Res.string.show_password,
    )
  }
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkMode = true,
  )
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkMode = false,
  )
}
