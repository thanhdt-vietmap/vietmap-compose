package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.MillisecondsValue
import vn.vietmap.vietmapcompose.expressions.value.RasterResampling

internal actual class RasterLayer actual constructor(id: String, actual val source: Source) :
  Layer() {
  override val impl = TODO()

  actual fun setRasterOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setRasterHueRotate(hueRotate: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setRasterBrightnessMin(brightnessMin: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setRasterBrightnessMax(brightnessMax: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setRasterSaturation(saturation: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setRasterContrast(contrast: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setRasterResampling(resampling: CompiledExpression<RasterResampling>) {
    TODO()
  }

  actual fun setRasterFadeDuration(fadeDuration: CompiledExpression<MillisecondsValue>) {
    TODO()
  }
}
