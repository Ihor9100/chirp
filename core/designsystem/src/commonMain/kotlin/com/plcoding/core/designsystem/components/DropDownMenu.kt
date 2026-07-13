package com.plcoding.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.getColor
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DropDownMenu(
  modifier: Modifier = Modifier,
  showMenu: Boolean,
  items: List<DropDownItemUi>,
  onAction: (DropDownItemUi) -> Unit,
  onDismiss: () -> Unit,
) {
  DropdownMenu(
    modifier = modifier,
    expanded = showMenu,
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(16.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    shadowElevation = 0.dp,
    border = BorderStroke(
      width = 1.dp,
      color = MaterialTheme.colorScheme.extended.surfaceOutline,
    ),
    offset = DpOffset(x=0.dp, y=16.dp)
  ) {
    items.forEachIndexed { index, itemPm ->
      DropdownMenuItem(
        text = {
          Text(
            text = stringResource(itemPm.titleRes),
            color = itemPm.colorToken.getColor(),
            style = MaterialTheme.typography.labelMedium,
          )
        },
        leadingIcon = itemPm.leadingIconRes?.let {
          {
            Icon(
              modifier = Modifier.size(16.dp),
              imageVector = vectorResource(it),
              contentDescription = null,
              tint = itemPm.colorToken.getColor()
            )
          }
        },
        onClick = { onAction(itemPm) },
      )
      if (index != items.lastIndex) {
        HorizontalDivider()
      }
    }
  }
}

@Composable
private fun Themed(
  isDarkMode: Boolean,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    DropDownMenu(
      showMenu = true,
      items = DropDownItemUi.mocks,
      onAction = {},
      onDismiss = {},
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
