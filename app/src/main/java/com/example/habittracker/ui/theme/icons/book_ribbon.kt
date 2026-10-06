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
public val book_ribbon: ImageVector
  get() {
    if (_book_ribbon != null) {
      return _book_ribbon!!
    }
    _book_ribbon =
      ImageVector.Builder(
          name = "book_ribbon",
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
            moveTo(12f, 20f)
            quadTo(10.8f, 19.05f, 9.4f, 18.52f)
            reflectiveQuadTo(6.5f, 18f)
            quadTo(5.45f, 18f, 4.44f, 18.27f)
            reflectiveQuadTo(2.5f, 19.05f)
            quadTo(1.98f, 19.33f, 1.49f, 19.02f)
            quadTo(1f, 18.73f, 1f, 18.15f)
            verticalLineTo(6.1f)
            quadTo(1f, 5.82f, 1.14f, 5.57f)
            quadTo(1.28f, 5.32f, 1.55f, 5.2f)
            quadTo(2.7f, 4.6f, 3.95f, 4.3f)
            reflectiveQuadTo(6.5f, 4f)
            quadTo(8.35f, 4f, 9.65f, 4.42f)
            reflectiveQuadToRelative(2.8f, 1.3f)
            quadToRelative(0.28f, 0.15f, 0.41f, 0.35f)
            reflectiveQuadTo(13f, 6.6f)
            verticalLineTo(17.05f)
            quadToRelative(1.1f, -0.53f, 2.21f, -0.79f)
            reflectiveQuadTo(17.5f, 16f)
            quadToRelative(0.9f, 0f, 1.76f, 0.15f)
            reflectiveQuadTo(21f, 16.6f)
            verticalLineTo(4.57f)
            quadToRelative(0.38f, 0.13f, 0.74f, 0.27f)
            reflectiveQuadTo(22.45f, 5.2f)
            quadToRelative(0.27f, 0.13f, 0.41f, 0.38f)
            reflectiveQuadTo(23f, 6.1f)
            verticalLineTo(18.15f)
            quadToRelative(0f, 0.58f, -0.49f, 0.88f)
            quadToRelative(-0.49f, 0.3f, -1.01f, 0.03f)
            quadToRelative(-0.92f, -0.5f, -1.94f, -0.78f)
            reflectiveQuadTo(17.5f, 18f)
            quadTo(16f, 18f, 14.6f, 18.52f)
            reflectiveQuadTo(12f, 20f)
            close()
            moveToRelative(3.5f, -6f)
            verticalLineTo(3f)
            lineToRelative(3f, -1f)
            verticalLineTo(13f)
            lineToRelative(-3f, 1f)
            close()
          }
        }
        .build()
    return _book_ribbon!!
  }

private var _book_ribbon: ImageVector? = null
