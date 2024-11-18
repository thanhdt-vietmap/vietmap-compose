package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal expect class RasterLayer(id: String, source: Source) : Layer {
  val source: Source

  fun setRasterOpacity(opacity: Expression<Number>)

  fun setRasterHueRotate(hueRotate: Expression<Number>)

  fun setRasterBrightnessMin(brightnessMin: Expression<Number>)

  fun setRasterBrightnessMax(brightnessMax: Expression<Number>)

  fun setRasterSaturation(saturation: Expression<Number>)

  fun setRasterContrast(contrast: Expression<Number>)

  fun setRasterResampling(resampling: Expression<String>)

  fun setRasterFadeDuration(fadeDuration: Expression<Number>)
}
