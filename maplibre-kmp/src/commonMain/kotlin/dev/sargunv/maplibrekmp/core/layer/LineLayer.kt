package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal expect class LineLayer(id: String, source: Source) : FeatureLayer {
  fun setLineCap(cap: Expression<String>)

  fun setLineJoin(join: Expression<String>)

  fun setLineMiterLimit(miterLimit: Expression<Number>)

  fun setLineRoundLimit(roundLimit: Expression<Number>)

  fun setLineSortKey(sortKey: Expression<Number>)

  fun setLineOpacity(opacity: Expression<Number>)

  fun setLineColor(color: Expression<Color>)

  fun setLineTranslate(translate: Expression<Point>)

  fun setLineTranslateAnchor(translateAnchor: Expression<String>)

  fun setLineWidth(width: Expression<Number>)

  fun setLineGapWidth(gapWidth: Expression<Number>)

  fun setLineOffset(offset: Expression<Number>)

  fun setLineBlur(blur: Expression<Number>)

  fun setLineDasharray(dasharray: Expression<List<Number>>)

  fun setLinePattern(pattern: Expression<TResolvedImage>)

  fun setLineGradient(gradient: Expression<Color>)
}
