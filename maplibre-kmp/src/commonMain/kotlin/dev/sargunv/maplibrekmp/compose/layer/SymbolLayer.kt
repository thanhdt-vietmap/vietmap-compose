package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.SymbolLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.Expression.Companion.point
import dev.sargunv.maplibrekmp.expression.Point
import io.github.dellisd.spatialk.geojson.Feature

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun SymbolLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  placement: Expression<String> = const("point"),
  spacing: Expression<Number> = const(250.0),
  avoidEdges: Expression<Boolean> = const(false),
  sortKey: Expression<Number> = nil(),
  zOrder: Expression<String> = const("auto"),
  iconAllowOverlap: Expression<Boolean> = const(false),
  iconIgnorePlacement: Expression<Boolean> = const(false),
  iconOptional: Expression<Boolean> = const(false),
  iconRotationAlignment: Expression<String> = const("auto"),
  iconSize: Expression<Number> = const(1.0),
  iconTextFit: Expression<String> = const("none"),
  iconTextFitPadding: Expression<Point> = point(0, 0),
  iconImage: Expression<String> = nil(),
  iconRotate: Expression<Number> = const(0.0),
  iconPadding: Expression<Number> = const(2.0),
  iconKeepUpright: Expression<Boolean> = const(false),
  iconOffset: Expression<Point> = point(0, 0),
  iconAnchor: Expression<String> = const("center"),
  iconPitchAlignment: Expression<String> = const("auto"),
  iconOpacity: Expression<Number> = const(1.0),
  iconColor: Expression<Color> = const(Color.Black),
  iconHaloColor: Expression<Color> = const(Color.Transparent),
  iconHaloWidth: Expression<Number> = const(0.0),
  iconHaloBlur: Expression<Number> = const(0.0),
  iconTranslate: Expression<Point> = point(0, 0),
  iconTranslateAnchor: Expression<String> = const("map"),
  // TODO text fields ...
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { SymbolLayer(id = id, source = source) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(placement) { layer.setSymbolPlacement(it) }
        set(spacing) { layer.setSymbolSpacing(it) }
        set(avoidEdges) { layer.setSymbolAvoidEdges(it) }
        set(sortKey) { layer.setSymbolSortKey(it) }
        set(zOrder) { layer.setSymbolZOrder(it) }
        set(iconAllowOverlap) { layer.setIconAllowOverlap(it) }
        set(iconIgnorePlacement) { layer.setIconIgnorePlacement(it) }
        set(iconOptional) { layer.setIconOptional(it) }
        set(iconRotationAlignment) { layer.setIconRotationAlignment(it) }
        set(iconSize) { layer.setIconSize(it) }
        set(iconTextFit) { layer.setIconTextFit(it) }
        set(iconTextFitPadding) { layer.setIconTextFitPadding(it) }
        set(iconImage) { layer.setIconImage(it) }
        set(iconRotate) { layer.setIconRotate(it) }
        set(iconPadding) { layer.setIconPadding(it) }
        set(iconKeepUpright) { layer.setIconKeepUpright(it) }
        set(iconOffset) { layer.setIconOffset(it) }
        set(iconAnchor) { layer.setIconAnchor(it) }
        set(iconPitchAlignment) { layer.setIconPitchAlignment(it) }
        set(iconOpacity) { layer.setIconOpacity(it) }
        set(iconColor) { layer.setIconColor(it) }
        set(iconHaloColor) { layer.setIconHaloColor(it) }
        set(iconHaloWidth) { layer.setIconHaloWidth(it) }
        set(iconHaloBlur) { layer.setIconHaloBlur(it) }
        set(iconTranslate) { layer.setIconTranslate(it) }
        set(iconTranslateAnchor) { layer.setIconTranslateAnchor(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
