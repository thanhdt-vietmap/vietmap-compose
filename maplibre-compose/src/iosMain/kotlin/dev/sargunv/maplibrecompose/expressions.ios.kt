package dev.sargunv.maplibrecompose

import cocoapods.MapLibre.expressionWithMLNJSONObject
import platform.Foundation.NSExpression

internal fun Expression<*>.toNSExpression() =
  value?.let { NSExpression.expressionWithMLNJSONObject(it) }
    ?: NSExpression.expressionForConstantValue(null)
