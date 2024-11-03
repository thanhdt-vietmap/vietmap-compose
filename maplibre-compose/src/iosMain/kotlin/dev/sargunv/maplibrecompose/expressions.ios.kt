package dev.sargunv.maplibrecompose

import cocoapods.MapLibre.MLNExpressionInterpolationModeExponential
import cocoapods.MapLibre.mgl_expressionForInterpolatingExpression
import cocoapods.MapLibre.zoomLevelVariableExpression
import platform.Foundation.*

internal fun fancyWidth(): NSExpression {
  return NSExpression.mgl_expressionForInterpolatingExpression(
    NSExpression.zoomLevelVariableExpression,
    MLNExpressionInterpolationModeExponential,
    NSExpression.expressionForConstantValue(2),
    NSExpression.expressionForConstantValue(
      mapOf(
        0 to NSExpression.expressionForConstantValue(1),
        10 to NSExpression.expressionForConstantValue(250)
      )
    )
  )
}
