package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.HillshadeLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import io.github.dellisd.spatialk.geojson.Feature
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun HillshadeLayer(
  id: String,
  source: Source,
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  visible: Boolean = true,
  illuminationDirection: Expression<Number> = const(355),
  illuminationAnchor: Expression<String> = const("viewport"),
  exaggeration: Expression<Number> = const(0.5),
  shadowColor: Expression<Color> = const(Color.Black),
  highlightColor: Expression<Color> = const(Color.White),
  accentColor: Expression<Color> = const(Color.Black),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { HillshadeLayer(id = id, source = source) },
      update = {
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(visible) { layer.visible = it }
        set(illuminationDirection) { layer.setHillshadeIlluminationDirection(it) }
        set(illuminationAnchor) { layer.setHillshadeIlluminationAnchor(it) }
        set(exaggeration) { layer.setHillshadeExaggeration(it) }
        set(shadowColor) { layer.setHillshadeShadowColor(it) }
        set(highlightColor) { layer.setHillshadeHighlightColor(it) }
        set(accentColor) { layer.setHillshadeAccentColor(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
