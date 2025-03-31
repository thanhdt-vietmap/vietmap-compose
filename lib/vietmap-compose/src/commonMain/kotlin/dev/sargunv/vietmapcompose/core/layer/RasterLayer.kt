package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.MillisecondsValue
import dev.sargunv.vietmapcompose.expressions.value.RasterResampling

internal expect class RasterLayer(id: String, source: Source) : Layer {
  val source: Source

  fun setRasterOpacity(opacity: CompiledExpression<FloatValue>)

  fun setRasterHueRotate(hueRotate: CompiledExpression<FloatValue>)

  fun setRasterBrightnessMin(brightnessMin: CompiledExpression<FloatValue>)

  fun setRasterBrightnessMax(brightnessMax: CompiledExpression<FloatValue>)

  fun setRasterSaturation(saturation: CompiledExpression<FloatValue>)

  fun setRasterContrast(contrast: CompiledExpression<FloatValue>)

  fun setRasterResampling(resampling: CompiledExpression<RasterResampling>)

  fun setRasterFadeDuration(fadeDuration: CompiledExpression<MillisecondsValue>)
}
