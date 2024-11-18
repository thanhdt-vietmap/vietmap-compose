package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.RasterLayer as MLNRasterLayer

@PublishedApi
internal actual class RasterLayer actual constructor(id: String, actual val source: Source) :
  Layer() {
  override val impl = MLNRasterLayer(id, source.id)

  actual fun setRasterOpacity(opacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.rasterOpacity(opacity.toMLNExpression()))
  }

  actual fun setRasterHueRotate(hueRotate: Expression<Number>) {
    impl.setProperties(PropertyFactory.rasterHueRotate(hueRotate.toMLNExpression()))
  }

  actual fun setRasterBrightnessMin(brightnessMin: Expression<Number>) {
    impl.setProperties(PropertyFactory.rasterBrightnessMin(brightnessMin.toMLNExpression()))
  }

  actual fun setRasterBrightnessMax(brightnessMax: Expression<Number>) {
    impl.setProperties(PropertyFactory.rasterBrightnessMax(brightnessMax.toMLNExpression()))
  }

  actual fun setRasterSaturation(saturation: Expression<Number>) {
    impl.setProperties(PropertyFactory.rasterSaturation(saturation.toMLNExpression()))
  }

  actual fun setRasterContrast(contrast: Expression<Number>) {
    impl.setProperties(PropertyFactory.rasterContrast(contrast.toMLNExpression()))
  }

  actual fun setRasterResampling(resampling: Expression<String>) {
    impl.setProperties(PropertyFactory.rasterResampling(resampling.toMLNExpression()))
  }

  actual fun setRasterFadeDuration(fadeDuration: Expression<Number>) {
    impl.setProperties(PropertyFactory.rasterFadeDuration(fadeDuration.toMLNExpression()))
  }
}
