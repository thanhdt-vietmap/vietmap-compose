package dev.sargunv.maplibrekmp.internal.wrapper.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Point

@PublishedApi
internal expect class CircleLayer(id: String, source: Source) : Layer {
  var sourceLayer: String

  fun setFilter(filter: Expression<Boolean>)

  fun setCircleSortKey(circleSortKey: Expression<Number>)

  fun setCircleRadius(circleRadius: Expression<Number>)

  fun setCircleColor(circleColor: Expression<Color>)

  fun setCircleBlur(circleBlur: Expression<Number>)

  fun setCircleOpacity(circleOpacity: Expression<Number>)

  fun setCircleTranslate(circleTranslate: Expression<Point>)

  fun setCircleTranslateAnchor(circleTranslateAnchor: Expression<String>)

  fun setCirclePitchScale(circlePitchScale: Expression<String>)

  fun setCirclePitchAlignment(circlePitchAlignment: Expression<String>)

  fun setCircleStrokeWidth(circleStrokeWidth: Expression<Number>)

  fun setCircleStrokeColor(circleStrokeColor: Expression<Color>)

  fun setCircleStrokeOpacity(circleStrokeOpacity: Expression<Number>)
}
