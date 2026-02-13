package com.plcoding.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.model.DropDownItemPm
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DropDownMenuPc(
  modifier: Modifier = Modifier,
  showMenu: Boolean,
  items: List<DropDownItemPm>,
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
  ) {
    items.forEachIndexed { index, itemPm ->
      DropdownMenuItem(
        text = {
          Text(
            text = stringResource(itemPm.titleRes),
            color = itemPm.color,
            style = MaterialTheme.typography.labelMedium,
          )
        },
        leadingIcon = itemPm.leadingIconRes?.let {
          {
            Icon(
              modifier = Modifier.size(16.dp),
              imageVector = vectorResource(it),
              contentDescription = null,
            )
          }
        },
        onClick = itemPm.onClick,
      )
      if (index != items.lastIndex) {
        HorizontalDividerPc()
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
    DropDownMenuPc(
      showMenu = true,
      items = DropDownItemPm.mocks,
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
