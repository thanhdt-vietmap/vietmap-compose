package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal expect class FillLayer(id: String, source: Source, anchor: Anchor) : UserFeatureLayer {
  fun setFillSortKey(fillSortKey: Expression<Number>)

  fun setFillAntialias(fillAntialias: Expression<Boolean>)

  fun setFillOpacity(fillOpacity: Expression<Number>)

  fun setFillColor(fillColor: Expression<Color>)

  fun setFillOutlineColor(fillOutlineColor: Expression<Color>)

  fun setFillTranslate(fillTranslate: Expression<Point>)

  fun setFillTranslateAnchor(fillTranslateAnchor: Expression<String>)

  fun setFillPattern(fillPattern: Expression<TResolvedImage>)
}
