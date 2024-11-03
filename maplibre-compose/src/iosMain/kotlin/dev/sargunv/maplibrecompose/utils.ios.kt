package dev.sargunv.maplibrecompose

import kotlinx.cinterop.initBy
import platform.Foundation.NSExpression
import platform.Foundation.NSExpressionMeta
import platform.UIKit.UIColor

internal fun Color.toUiColor(): UIColor {
  return UIColor(
    red = red.toDouble() / 255.0,
    green = green.toDouble() / 255.0,
    blue = blue.toDouble() / 255.0,
    alpha = alpha.toDouble(),
  )
}
