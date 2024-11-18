package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal expect class FillLayer(id: String, source: Source) : FeatureLayer {
  fun setFillSortKey(sortKey: Expression<Number>)

  fun setFillAntialias(antialias: Expression<Boolean>)

  fun setFillOpacity(opacity: Expression<Number>)

  fun setFillColor(color: Expression<Color>)

  fun setFillOutlineColor(outlineColor: Expression<Color>)

  fun setFillTranslate(translate: Expression<Point>)

  fun setFillTranslateAnchor(translateAnchor: Expression<String>)

  fun setFillPattern(pattern: Expression<TResolvedImage>)
}
