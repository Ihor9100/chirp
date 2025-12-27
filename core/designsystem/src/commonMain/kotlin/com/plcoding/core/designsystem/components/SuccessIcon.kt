package com.plcoding.core.designsystem.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_success_checkmark
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.vectorResource

@Composable
fun SuccessIcon(
  modifier: Modifier = Modifier,
) {
  Icon(
    imageVector = vectorResource(Res.drawable.ic_success_checkmark),
    contentDescription = null,
    modifier = modifier,
    tint = MaterialTheme.colorScheme.extended.success,
  )
}
