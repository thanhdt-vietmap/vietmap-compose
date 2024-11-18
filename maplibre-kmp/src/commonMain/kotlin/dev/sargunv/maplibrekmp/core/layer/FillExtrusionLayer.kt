package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal expect class FillExtrusionLayer(id: String, source: Source) : FeatureLayer {
  fun setFillExtrusionOpacity(opacity: Expression<Number>)

  fun setFillExtrusionColor(color: Expression<Color>)

  fun setFillExtrusionTranslate(translate: Expression<Point>)

  fun setFillExtrusionTranslateAnchor(anchor: Expression<String>)

  fun setFillExtrusionPattern(pattern: Expression<TResolvedImage>)

  fun setFillExtrusionHeight(height: Expression<Number>)

  fun setFillExtrusionBase(base: Expression<Number>)

  fun setFillExtrusionVerticalGradient(verticalGradient: Expression<Boolean>)
}
