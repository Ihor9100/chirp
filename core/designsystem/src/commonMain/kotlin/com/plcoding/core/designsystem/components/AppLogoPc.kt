package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_logo_chirp
import org.jetbrains.compose.resources.vectorResource

@Composable
fun AppLogoPc(
  modifier: Modifier = Modifier,
) {
  Icon(
    modifier = modifier.size(32.dp),
    imageVector = vectorResource(Res.drawable.ic_logo_chirp),
    contentDescription = null,
    tint = MaterialTheme.colorScheme.primary
  )
}
