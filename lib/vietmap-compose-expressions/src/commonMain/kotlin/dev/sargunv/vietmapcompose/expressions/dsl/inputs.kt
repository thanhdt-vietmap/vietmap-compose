package dev.sargunv.vietmapcompose.expressions.dsl

import dev.sargunv.vietmapcompose.expressions.ast.Expression
import dev.sargunv.vietmapcompose.expressions.ast.FunctionCall
import dev.sargunv.vietmapcompose.expressions.value.FloatValue

/**
 * Gets the current zoom level. Note that in layer style properties, [zoom] may only appear as the
 * input to a top-level [step] or [interpolate] (, [interpolateHcl], [interpolateLab], ...)
 * expression.
 */
public fun zoom(): Expression<FloatValue> = FunctionCall.of("zoom").cast()

/**
 * Gets the kernel density estimation of a pixel in a heatmap layer, which is a relative measure of
 * how many data points are crowded around a particular pixel. Can only be used in the expression
 * for the `color` parameter in a HeatmapLayer
 * [HeatmapLayer][dev.sargunv.vietmapcompose.compose.layer.HeatmapLayer].
 */
public fun heatmapDensity(): Expression<FloatValue> = FunctionCall.of("heatmap-density").cast()
