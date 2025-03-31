package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.MillisecondsValue
import vn.vietmap.vietmapcompose.expressions.value.RasterResampling

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
