package dev.sargunv.maplibrecompose.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrecompose.core.expression.Expression
import dev.sargunv.maplibrecompose.core.expression.Point
import dev.sargunv.maplibrecompose.core.source.Source

@PublishedApi
internal expect class CircleLayer(id: String, source: Source) : FeatureLayer {
  override var sourceLayer: String

  override fun setFilter(filter: Expression<Boolean>)

  fun setCircleSortKey(sortKey: Expression<Number>)

  fun setCircleRadius(radius: Expression<Number>)

  fun setCircleColor(color: Expression<Color>)

  fun setCircleBlur(blur: Expression<Number>)

  fun setCircleOpacity(opacity: Expression<Number>)

  fun setCircleTranslate(translate: Expression<Point>)

  fun setCircleTranslateAnchor(translateAnchor: Expression<String>)

  fun setCirclePitchScale(pitchScale: Expression<String>)

  fun setCirclePitchAlignment(pitchAlignment: Expression<String>)

  fun setCircleStrokeWidth(strokeWidth: Expression<Number>)

  fun setCircleStrokeColor(strokeColor: Expression<Color>)

  fun setCircleStrokeOpacity(strokeOpacity: Expression<Number>)
}
