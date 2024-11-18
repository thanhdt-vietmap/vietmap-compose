package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression

internal expect class RasterLayer(id: String, source: Source) : Layer {
  fun setRasterOpacity(expression: Expression<Number>)

  fun setRasterHueRotate(expression: Expression<Number>)

  fun setRasterBrightnessMin(expression: Expression<Number>)

  fun setRasterBrightnessMax(expression: Expression<Number>)

  fun setRasterSaturation(expression: Expression<Number>)

  fun setRasterContrast(expression: Expression<Number>)

  fun setRasterResampling(expression: Expression<String>)

  fun setRasterFadeDuration(expression: Expression<Number>)
}
