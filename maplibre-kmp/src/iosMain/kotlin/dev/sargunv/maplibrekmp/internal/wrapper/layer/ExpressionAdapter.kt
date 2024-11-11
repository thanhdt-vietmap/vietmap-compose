package dev.sargunv.maplibrekmp.internal.wrapper.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.expressionWithMLNJSONObject
import cocoapods.MapLibre.predicateWithMLNJSONObject
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Point
import platform.CoreGraphics.CGVectorMake
import platform.Foundation.NSExpression
import platform.Foundation.NSPredicate
import platform.Foundation.NSValue
import platform.UIKit.UIColor
import platform.UIKit.valueWithCGVector

internal object ExpressionAdapter {

  fun Expression<*>.toNSExpression(): NSExpression =
    when (value) {
      null -> NSExpression.expressionForConstantValue(null)
      else -> NSExpression.expressionWithMLNJSONObject(normalizeJsonLike(value)!!)
    }

  fun Expression<Boolean>.toPredicate(): NSPredicate? =
    value?.let { NSPredicate.predicateWithMLNJSONObject(normalizeJsonLike(it)!!) }

  private fun normalizeJsonLike(value: Any?): Any? =
    when (value) {
      null -> null
      is Boolean -> value
      is Number -> value
      is String -> value
      is List<*> -> value.map(ExpressionAdapter::normalizeJsonLike)
      is Map<*, *> -> value.mapValues { normalizeJsonLike(it.value) }
      is Point -> NSValue.valueWithCGVector(CGVectorMake(value.x.toDouble(), value.y.toDouble()))
      is Color ->
        UIColor.colorWithRed(
          red = value.red.toDouble(),
          green = value.green.toDouble(),
          blue = value.blue.toDouble(),
          alpha = value.alpha.toDouble(),
        )

      else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
    }
}
