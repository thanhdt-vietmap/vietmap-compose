package dev.sargunv.maplibrecompose

import cocoapods.MapLibre.expressionWithMLNJSONObject
import platform.Foundation.NSExpression
import platform.UIKit.UIColor

internal fun Expression<*>.toNSExpression() =
  value?.let { NSExpression.expressionWithMLNJSONObject(it) }
    ?: NSExpression.expressionForConstantValue(null)

public actual class Color internal constructor(color: UIColor) : Expression<TColor> {
  @Suppress("RedundantNullableReturnType") override val value: Any? = color
}

public actual object Colors {
  public actual val white: Color = Color(UIColor.whiteColor)
  public actual val silver: Color = Color(UIColor.grayColor)
  public actual val gray: Color = Color(UIColor.grayColor)
  public actual val black: Color = Color(UIColor.blackColor)
  public actual val red: Color = Color(UIColor.redColor)
  public actual val maroon: Color = Color(UIColor.redColor)
  public actual val yellow: Color = Color(UIColor.yellowColor)
  public actual val green: Color = Color(UIColor.greenColor)
  public actual val blue: Color = Color(UIColor.blueColor)
  public actual val purple: Color = Color(UIColor.purpleColor)

  public actual fun rgb(red: UByte, green: UByte, blue: UByte, alpha: Float?): Color {
    return Color(
      UIColor.colorWithRed(
        red.toDouble() / 255.0,
        green.toDouble() / 255.0,
        blue.toDouble() / 255.0,
        alpha?.toDouble() ?: 1.0,
      )
    )
  }

  public actual fun hsl(hue: Int, saturation: Int, lightness: Int, alpha: Float?): Color {
    return Color(
      UIColor.colorWithHue(
        hue.toDouble() / 360.0,
        saturation.toDouble() / 100.0,
        lightness.toDouble() / 100.0,
        alpha?.toDouble() ?: 1.0,
      )
    )
  }
}
