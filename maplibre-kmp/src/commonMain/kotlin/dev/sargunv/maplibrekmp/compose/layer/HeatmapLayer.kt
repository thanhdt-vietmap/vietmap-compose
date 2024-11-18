package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.HeatmapLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.heatmapDensity
import dev.sargunv.maplibrekmp.expression.Expression.Companion.interpolate
import dev.sargunv.maplibrekmp.expression.Expression.Companion.linear
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import io.github.dellisd.spatialk.geojson.Feature
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun HeatmapLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  radius: Expression<Number> = const(30),
  weight: Expression<Number> = const(1),
  intensity: Expression<Number> = const(1),
  color: Expression<Color> =
    interpolate(
      linear(),
      heatmapDensity(),
      0 to const(Color.Transparent),
      0.1 to const(Color(0xFF4169E1)), // royal blue
      0.3 to const(Color(0xFF00FFFF)), // cyan
      0.5 to const(Color(0xFF00FF00)), // lime
      0.7 to const(Color(0xFFFFFF00)), // yellow
      1 to const(Color(0xFFFF0000)), // red
    ),
  opacity: Expression<Number> = const(1),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { HeatmapLayer(id = id, source = source) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(radius) { layer.setHeatmapRadius(it) }
        set(weight) { layer.setHeatmapWeight(it) }
        set(intensity) { layer.setHeatmapIntensity(it) }
        set(color) { layer.setHeatmapColor(it) }
        set(opacity) { layer.setHeatmapOpacity(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
