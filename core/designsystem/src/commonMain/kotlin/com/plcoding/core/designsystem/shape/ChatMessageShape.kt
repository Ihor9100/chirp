package com.plcoding.core.designsystem.shape

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.plcoding.core.designsystem.model.AnchorPositionPm

class ChatMessageShape(
  private val anchorPositionPm: AnchorPositionPm,
  private val anchorSizeDp: Dp,
) : Shape {

  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density,
  ): Outline {
    val anchorSizePx = with(density) { anchorSizeDp.toPx() }

    val finalPath = when (anchorPositionPm) {
      AnchorPositionPm.LEFT -> {
        val bodyPath = Path().apply {
          addRoundRect(
            roundRect = RoundRect(
              left = anchorSizePx,
              top = 0f,
              right = size.width,
              bottom = size.height,
              cornerRadius = CornerRadius(
                x = anchorSizePx,
                y = anchorSizePx,
              )
            )
          )
        }
        val anchorPath = Path().apply {
          moveTo(0f, size.height)
          lineTo(anchorSizePx, size.height - anchorSizePx)
          lineTo(anchorSizePx + anchorSizePx, size.height)
          close()
        }
        Path.combine(PathOperation.Union, bodyPath, anchorPath)
      }
      AnchorPositionPm.RIGHT -> {
        val bodyPath = Path().apply {
          addRoundRect(
            roundRect = RoundRect(
              left = 0f,
              top = 0f,
              right = size.width - anchorSizePx,
              bottom = size.height,
              cornerRadius = CornerRadius(
                x = anchorSizePx,
                y = anchorSizePx,
              )
            )
          )
        }
        val anchorPath = Path().apply {
          moveTo(size.width, size.height)
          lineTo(size.width - anchorSizePx, size.height - anchorSizePx)
          lineTo(size.height - anchorSizePx - anchorSizePx, size.height)
          close()
        }
        Path.combine(PathOperation.Union, bodyPath, anchorPath)
      }
    }

    return Outline.Generic(finalPath)
  }
}
