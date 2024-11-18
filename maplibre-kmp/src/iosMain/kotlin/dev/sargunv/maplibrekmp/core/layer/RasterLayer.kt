package dev.sargunv.maplibrekmp.core.layer

import cocoapods.MapLibre.MLNRasterStyleLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toNSExpression
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal actual class RasterLayer actual constructor(id: String, actual val source: Source) :
  Layer() {

  override val impl = MLNRasterStyleLayer(id, source.impl)

  actual fun setRasterOpacity(opacity: Expression<Number>) {
    impl.rasterOpacity = opacity.toNSExpression()
  }

  actual fun setRasterHueRotate(hueRotate: Expression<Number>) {
    impl.rasterHueRotation = hueRotate.toNSExpression()
  }

  actual fun setRasterBrightnessMin(brightnessMin: Expression<Number>) {
    impl.minimumRasterBrightness = brightnessMin.toNSExpression()
  }

  actual fun setRasterBrightnessMax(brightnessMax: Expression<Number>) {
    impl.maximumRasterBrightness = brightnessMax.toNSExpression()
  }

  actual fun setRasterSaturation(saturation: Expression<Number>) {
    impl.rasterSaturation = saturation.toNSExpression()
  }

  actual fun setRasterContrast(contrast: Expression<Number>) {
    impl.rasterContrast = contrast.toNSExpression()
  }

  actual fun setRasterResampling(resampling: Expression<String>) {
    impl.rasterResamplingMode = resampling.toNSExpression()
  }

  actual fun setRasterFadeDuration(fadeDuration: Expression<Number>) {
    impl.rasterFadeDuration = fadeDuration.toNSExpression()
  }
}
