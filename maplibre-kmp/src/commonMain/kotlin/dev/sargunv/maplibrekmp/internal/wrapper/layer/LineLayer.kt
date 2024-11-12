package dev.sargunv.maplibrekmp.internal.wrapper.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Point
import dev.sargunv.maplibrekmp.style.expression.TResolvedImage

@PublishedApi
internal expect class LineLayer(id: String, source: Source) : Layer {
  var sourceLayer: String

  fun setFilter(filter: Expression<Boolean>)

  fun setLineCap(lineCap: Expression<String>)

  fun setLineJoin(lineJoin: Expression<String>)

  fun setLineMiterLimit(lineMiterLimit: Expression<Number>)

  fun setLineRoundLimit(lineRoundLimit: Expression<Number>)

  fun setLineSortKey(lineSortKey: Expression<Number>)

  fun setLineOpacity(lineOpacity: Expression<Number>)

  fun setLineColor(lineColor: Expression<Color>)

  fun setLineTranslate(lineTranslate: Expression<Point>)

  fun setLineTranslateAnchor(lineTranslateAnchor: Expression<String>)

  fun setLineWidth(lineWidth: Expression<Number>)

  fun setLineGapWidth(lineGapWidth: Expression<Number>)

  fun setLineOffset(lineOffset: Expression<Number>)

  fun setLineBlur(lineBlur: Expression<Number>)

  fun setLineDasharray(lineDasharray: Expression<List<Number>>)

  fun setLinePattern(linePattern: Expression<TResolvedImage>)

  fun setLineGradient(lineGradient: Expression<Color>)
}
