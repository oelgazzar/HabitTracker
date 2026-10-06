package com.example.test

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val directions_run: ImageVector
  get() {
    if (_directions_run != null) {
      return _directions_run!!
    }
    _directions_run =
      ImageVector.Builder(
          name = "directions_run",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.Companion.NonZero,
          ) {
            moveTo(13f, 23f)
            verticalLineTo(17f)
            lineTo(10.9f, 15f)
            lineToRelative(-1f, 4.4f)
            lineTo(3f, 18f)
            lineTo(3.4f, 16f)
            lineToRelative(4.8f, 1f)
            lineTo(9.8f, 8.9f)
            lineTo(8f, 9.6f)
            verticalLineTo(13f)
            horizontalLineTo(6f)
            verticalLineTo(8.3f)
            lineTo(9.95f, 6.6f)
            quadTo(10.83f, 6.22f, 11.24f, 6.11f)
            reflectiveQuadTo(12f, 6f)
            quadToRelative(0.53f, 0f, 0.98f, 0.27f)
            reflectiveQuadTo(13.7f, 7f)
            lineToRelative(1f, 1.6f)
            quadToRelative(0.65f, 1.05f, 1.76f, 1.72f)
            reflectiveQuadTo(19f, 11f)
            verticalLineToRelative(2f)
            quadToRelative(-1.65f, 0f, -3.09f, -0.69f)
            reflectiveQuadTo(13.5f, 10.5f)
            lineToRelative(-0.6f, 3f)
            lineToRelative(2.1f, 2f)
            verticalLineTo(23f)
            horizontalLineTo(13f)
            close()
            moveTo(12.09f, 4.91f)
            quadTo(11.5f, 4.32f, 11.5f, 3.5f)
            quadToRelative(0f, -0.83f, 0.59f, -1.41f)
            reflectiveQuadTo(13.5f, 1.5f)
            reflectiveQuadToRelative(1.41f, 0.59f)
            reflectiveQuadTo(15.5f, 3.5f)
            quadToRelative(0f, 0.82f, -0.59f, 1.41f)
            reflectiveQuadTo(13.5f, 5.5f)
            reflectiveQuadTo(12.09f, 4.91f)
            close()
          }
        }
        .build()
    return _directions_run!!
  }

private var _directions_run: ImageVector? = null
