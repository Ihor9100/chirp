package com.plcoding.core.designsystem.shape

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

enum class AnchorPosition {
  LEFT,
  RIGHT
}

class ChatMessageShape(
  private val anchorPosition: AnchorPosition,
) : Shape {

  companion object {
    private val triangleSizeDp = 16.dp
    private val cornerRadiusDp = 16.dp
  }

  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density,
  ): Outline {
    val triangleSizePx = with(density) { triangleSizeDp.toPx() }
    val cornerRadiusPx = with(density) { cornerRadiusDp.toPx() }

    val finalPath = when (anchorPosition) {
      AnchorPosition.LEFT -> {
        val bodyPath = Path().apply {
          addRoundRect(
            roundRect = RoundRect(
              left = triangleSizePx,
              top = 0f,
              right = size.width,
              bottom = size.height,
              cornerRadius = CornerRadius(
                x = cornerRadiusPx,
                y = cornerRadiusPx,
              )
            )
          )
        }
        val anchorPath = Path().apply {
          moveTo(0f, size.height)
          lineTo(triangleSizePx, size.height - cornerRadiusPx)
          lineTo(triangleSizePx + cornerRadiusPx, size.height)
          close()
        }
        Path.combine(PathOperation.Union, bodyPath, anchorPath)
      }
      AnchorPosition.RIGHT -> {
        val bodyPath = Path().apply {
          addRoundRect(
            roundRect = RoundRect(
              left = 0f,
              top = 0f,
              right = size.width - triangleSizePx,
              bottom = size.height,
              cornerRadius = CornerRadius(
                x = cornerRadiusPx,
                y = cornerRadiusPx,
              )
            )
          )
        }
        val anchorPath = Path().apply {
          moveTo(size.width, size.height)
          lineTo(size.width - triangleSizePx, size.height - cornerRadiusPx)
          lineTo(size.height - triangleSizePx - cornerRadiusPx, size.height)
          close()
        }
        Path.combine(PathOperation.Union, bodyPath, anchorPath)
      }
    }

    return Outline.Generic(finalPath)
  }
}
