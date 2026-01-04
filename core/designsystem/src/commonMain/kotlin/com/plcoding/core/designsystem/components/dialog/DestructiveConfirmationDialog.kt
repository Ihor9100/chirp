package com.plcoding.core.designsystem.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_cross
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DestructiveConfirmationDialog(
  modifier: Modifier = Modifier,
  title: String,
  description: String,
  confirmButtonText: String,
  cancelButtonText: String,
  onConfirmClick: () -> Unit,
  onCancelClick: () -> Unit,
  onDismiss: () -> Unit,
) {
  Dialog(
    onDismissRequest = onDismiss,
  ) {
    Column(
      modifier = modifier
        .fillMaxWidth()
        .background(
          color = MaterialTheme.colorScheme.surface,
          shape = RoundedCornerShape(16.dp)
        )
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Column(
          modifier = Modifier
            .weight(1f),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.extended.textSecondary
          )
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.extended.textSecondary
          )
        }
        Icon(
          modifier = Modifier
            .size(20.dp),
          imageVector = vectorResource(Res.drawable.ic_cross),
          contentDescription = null,
          tint = MaterialTheme.colorScheme.extended.textSecondary,
        )
      }
      Spacer(Modifier.height(20.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
      ) {
        Button(
          modifier = Modifier,
          text = cancelButtonText,
          style = ButtonStyle.SECONDARY,
          onClick = onCancelClick,
        )
        Button(
          modifier = Modifier,
          text = confirmButtonText,
          style = ButtonStyle.DESTRUCTIVE_PRIMARY,
          onClick = onConfirmClick,
        )
      }
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  Theme(isDarkTheme) {
    DestructiveConfirmationDialog(
      title = "Delete profile picture?",
      description = "This will permanently delete your profile picture. This cannot be undone.",
      confirmButtonText = "Delete",
      cancelButtonText = "Cancel",
      onConfirmClick = {},
      onCancelClick = {},
      onDismiss = {}
    )
  }
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkTheme = false,
  )
}


@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkTheme = true,
  )
}
