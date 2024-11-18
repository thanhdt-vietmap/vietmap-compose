package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import dev.sargunv.maplibrekmp.core.layer.RasterLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import io.github.dellisd.spatialk.geojson.Feature
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun RasterLayer(
  id: String,
  source: Source,
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  visible: Boolean = true,
  opacity: Expression<Number> = const(1),
  hueRotate: Expression<Number> = const(0),
  brightnessMin: Expression<Number> = const(0),
  brightnessMax: Expression<Number> = const(1),
  saturation: Expression<Number> = const(0),
  contrast: Expression<Number> = const(0),
  resampling: Expression<String> = const("linear"),
  fadeDuration: Expression<Number> = const(300),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { RasterLayer(id = id, source = source) },
      update = {
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(visible) { layer.visible = it }
        set(opacity) { layer.setRasterOpacity(it) }
        set(hueRotate) { layer.setRasterHueRotate(it) }
        set(brightnessMin) { layer.setRasterBrightnessMin(it) }
        set(brightnessMax) { layer.setRasterBrightnessMax(it) }
        set(saturation) { layer.setRasterSaturation(it) }
        set(contrast) { layer.setRasterContrast(it) }
        set(resampling) { layer.setRasterResampling(it) }
        set(fadeDuration) { layer.setRasterFadeDuration(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
