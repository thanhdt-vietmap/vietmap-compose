package dev.sargunv.maplibrecompose

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.expressionWithMLNJSONObject
import platform.Foundation.NSExpression
import platform.UIKit.UIColor

internal fun Expression<*>.toNSExpression() =
  value?.let { NSExpression.expressionWithMLNJSONObject(it) }
    ?: NSExpression.expressionForConstantValue(null)

internal actual fun Color.toMlnColor(): Any =
  UIColor.colorWithRed(
    red = red.toDouble(),
    green = green.toDouble(),
    blue = blue.toDouble(),
    alpha = alpha.toDouble(),
  )
