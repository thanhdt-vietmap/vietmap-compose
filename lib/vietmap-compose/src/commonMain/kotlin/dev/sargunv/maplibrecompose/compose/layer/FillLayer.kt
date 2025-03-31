package dev.sargunv.maplibrecompose.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import dev.sargunv.maplibrecompose.compose.FeaturesClickHandler
import dev.sargunv.maplibrecompose.compose.MaplibreComposable
import dev.sargunv.maplibrecompose.core.layer.FillLayer
import dev.sargunv.maplibrecompose.core.source.Source
import dev.sargunv.maplibrecompose.expressions.ast.Expression
import dev.sargunv.maplibrecompose.expressions.dsl.const
import dev.sargunv.maplibrecompose.expressions.dsl.nil
import dev.sargunv.maplibrecompose.expressions.value.BooleanValue
import dev.sargunv.maplibrecompose.expressions.value.ColorValue
import dev.sargunv.maplibrecompose.expressions.value.DpOffsetValue
import dev.sargunv.maplibrecompose.expressions.value.FloatValue
import dev.sargunv.maplibrecompose.expressions.value.ImageValue
import dev.sargunv.maplibrecompose.expressions.value.TranslateAnchor

/**
 * A fill layer draws polygons from the [sourceLayer] in the given [source] in the given style as a
 * series of polygon fills. If nothing else is specified, these will be black.
 *
 * @param id Unique layer name.
 * @param source Vector data source for this layer.
 * @param sourceLayer Layer to use from the given vector tile [source].
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than this, the layer
 *   will be hidden. A value in the range of `[0..24]`.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than
 *   this, the layer will be hidden. A value in the range of `[0..24]`.
 * @param filter An expression specifying conditions on source features. Only features that match
 *   the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom
 *   levels. The [featureState][dev.sargunv.maplibrecompose.expressions.dsl.Feature.state]
 *   expression is not supported in filter expressions.
 * @param visible Whether the layer should be displayed.
 * @param sortKey Sorts features within this layer in ascending order based on this value. Features
 *   with a higher sort key will appear above features with a lower sort key.
 * @param translate The geometry's offset relative to the [translateAnchor]. Negative numbers
 *   indicate left and up, respectively.
 * @param translateAnchor Frame of reference for offsetting geometry.
 *
 *   Ignored if [translate] is not set.
 *
 * @param opacity Fill opacity. A value in range `[0..1]`.
 * @param color Fill color.
 *
 *   Ignored if [pattern] is specified.
 *
 * @param pattern Image to use for drawing image fills. For seamless patterns, image width and
 *   height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will
 *   be evaluated only at integer zoom levels.
 * @param antialias Whether or not the fill should be antialiased.
 * @param outlineColor The outline color of the fill. The outline is drawn at a hairline width.
 *
 *   Ignored if [antialias] is `false`.
 *
 * @param onClick Function to call when any feature in this layer has been clicked.
 * @param onLongClick Function to call when any feature in this layer has been long-clicked.
 */
@Composable
@MaplibreComposable
public fun FillLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<BooleanValue> = nil(),
  visible: Boolean = true,
  sortKey: Expression<FloatValue> = nil(),
  translate: Expression<DpOffsetValue> = const(DpOffset.Zero),
  translateAnchor: Expression<TranslateAnchor> = const(TranslateAnchor.Map),
  opacity: Expression<FloatValue> = const(1f),
  color: Expression<ColorValue> = const(Color.Black),
  pattern: Expression<ImageValue> = nil(),
  antialias: Expression<BooleanValue> = const(true),
  outlineColor: Expression<ColorValue> = color,
  onClick: FeaturesClickHandler? = null,
  onLongClick: FeaturesClickHandler? = null,
) {
  val compile = rememberPropertyCompiler()

  val compiledFilter = compile(filter)
  val compiledSortKey = compile(sortKey)
  val compiledTranslate = compile(translate)
  val compiledAntialias = compile(antialias)
  val compiledOpacity = compile(opacity)
  val compiledColor = compile(color)
  val compiledPattern = compile(pattern)
  val compiledTranslateAnchor = compile(translateAnchor)
  val compiledOutlineColor = compile(outlineColor)

  LayerNode(
    factory = { FillLayer(id = id, source = source) },
    update = {
      set(sourceLayer) { layer.sourceLayer = it }
      set(minZoom) { layer.minZoom = it }
      set(maxZoom) { layer.maxZoom = it }
      set(compiledFilter) { layer.setFilter(it) }
      set(visible) { layer.visible = it }
      set(compiledSortKey) { layer.setFillSortKey(it) }
      set(compiledAntialias) { layer.setFillAntialias(it) }
      set(compiledOpacity) { layer.setFillOpacity(it) }
      set(compiledColor) { layer.setFillColor(it) }
      set(compiledOutlineColor) { layer.setFillOutlineColor(it) }
      set(compiledTranslate) { layer.setFillTranslate(it) }
      set(compiledTranslateAnchor) { layer.setFillTranslateAnchor(it) }
      set(compiledPattern) { layer.setFillPattern(it) }
    },
    onClick = onClick,
    onLongClick = onLongClick,
  )
}
