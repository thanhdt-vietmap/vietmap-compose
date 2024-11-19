package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.SymbolLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.insets
import dev.sargunv.maplibrekmp.expression.Expression.Companion.literal
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.Expression.Companion.point
import dev.sargunv.maplibrekmp.expression.Insets
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TFormatted
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import io.github.dellisd.spatialk.geojson.Feature
import androidx.compose.runtime.key as composeKey

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
  iconTextFitPadding: Expression<Insets> = insets(0, 0, 0, 0),
  iconImage: Expression<TResolvedImage> = nil(),
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
  textPitchAlignment: Expression<String> = const("auto"),
  textRotationAlignment: Expression<String> = const("auto"),
  textField: Expression<TFormatted> = nil(),
  textFont: Expression<List<String>> =
    literal(listOf(const("Open Sans Regular"), const("Arial Unicode MS Regular"))),
  textSize: Expression<Number> = const(16.0),
  textMaxWidth: Expression<Number> = const(10.0),
  textLineHeight: Expression<Number> = const(1.2),
  textLetterSpacing: Expression<Number> = const(0.0),
  textJustify: Expression<String> = const("center"),
  textRadialOffset: Expression<Number> = const(0.0),
  textVariableAnchor: Expression<List<String>> = nil(),
  textVariableAnchorOffset: Expression<List<*>> = nil(),
  textAnchor: Expression<String> = const("center"),
  textMaxAngle: Expression<Number> = const(45.0),
  textWritingMode: Expression<List<String>> = nil(),
  textRotate: Expression<Number> = const(0.0),
  textPadding: Expression<Number> = const(2.0),
  textKeepUpright: Expression<Boolean> = const(true),
  textTransform: Expression<String> = const("none"),
  textOffset: Expression<Point> = point(0, 0),
  textAllowOverlap: Expression<Boolean> = const(false),
  textIgnorePlacement: Expression<Boolean> = const(false),
  textOptional: Expression<Boolean> = const(false),
  textOpacity: Expression<Number> = const(1.0),
  textColor: Expression<Color> = const(Color.Black),
  textHaloColor: Expression<Color> = const(Color.Transparent),
  textHaloWidth: Expression<Number> = const(0.0),
  textHaloBlur: Expression<Number> = const(0.0),
  textTranslate: Expression<Point> = point(0, 0),
  textTranslateAnchor: Expression<String> = const("map"),
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
        set(textPitchAlignment) { layer.setTextPitchAlignment(it) }
        set(textRotationAlignment) { layer.setTextRotationAlignment(it) }
        set(textField) { layer.setTextField(it) }
        set(textFont) { layer.setTextFont(it) }
        set(textSize) { layer.setTextSize(it) }
        set(textMaxWidth) { layer.setTextMaxWidth(it) }
        set(textLineHeight) { layer.setTextLineHeight(it) }
        set(textLetterSpacing) { layer.setTextLetterSpacing(it) }
        set(textJustify) { layer.setTextJustify(it) }
        set(textRadialOffset) { layer.setTextRadialOffset(it) }
        set(textVariableAnchor) { layer.setTextVariableAnchor(it) }
        set(textVariableAnchorOffset) { layer.setTextVariableAnchorOffset(it) }
        set(textAnchor) { layer.setTextAnchor(it) }
        set(textMaxAngle) { layer.setTextMaxAngle(it) }
        set(textWritingMode) { layer.setTextWritingMode(it) }
        set(textRotate) { layer.setTextRotate(it) }
        set(textPadding) { layer.setTextPadding(it) }
        set(textKeepUpright) { layer.setTextKeepUpright(it) }
        set(textTransform) { layer.setTextTransform(it) }
        set(textOffset) { layer.setTextOffset(it) }
        set(textAllowOverlap) { layer.setTextAllowOverlap(it) }
        set(textIgnorePlacement) { layer.setTextIgnorePlacement(it) }
        set(textOptional) { layer.setTextOptional(it) }
        set(textOpacity) { layer.setTextOpacity(it) }
        set(textColor) { layer.setTextColor(it) }
        set(textHaloColor) { layer.setTextHaloColor(it) }
        set(textHaloWidth) { layer.setTextHaloWidth(it) }
        set(textHaloBlur) { layer.setTextHaloBlur(it) }
        set(textTranslate) { layer.setTextTranslate(it) }
        set(textTranslateAnchor) { layer.setTextTranslateAnchor(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
