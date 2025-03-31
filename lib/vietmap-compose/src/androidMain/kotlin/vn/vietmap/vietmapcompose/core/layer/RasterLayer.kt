package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.core.util.toMLNExpression
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.MillisecondsValue
import vn.vietmap.vietmapcompose.expressions.value.RasterResampling
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.layers.RasterLayer as MLNRasterLayer

internal actual class RasterLayer actual constructor(id: String, actual val source: Source) :
  Layer() {
  override val impl = MLNRasterLayer(id, source.id)

  actual fun setRasterOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterOpacity(opacity.toMLNExpression()))
  }

  actual fun setRasterHueRotate(hueRotate: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterHueRotate(hueRotate.toMLNExpression()))
  }

  actual fun setRasterBrightnessMin(brightnessMin: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterBrightnessMin(brightnessMin.toMLNExpression()))
  }

  actual fun setRasterBrightnessMax(brightnessMax: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterBrightnessMax(brightnessMax.toMLNExpression()))
  }

  actual fun setRasterSaturation(saturation: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterSaturation(saturation.toMLNExpression()))
  }

  actual fun setRasterContrast(contrast: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterContrast(contrast.toMLNExpression()))
  }

  actual fun setRasterResampling(resampling: CompiledExpression<RasterResampling>) {
    impl.setProperties(PropertyFactory.rasterResampling(resampling.toMLNExpression()))
  }

  actual fun setRasterFadeDuration(fadeDuration: CompiledExpression<MillisecondsValue>) {
    impl.setProperties(PropertyFactory.rasterFadeDuration(fadeDuration.toMLNExpression()))
  }
}
