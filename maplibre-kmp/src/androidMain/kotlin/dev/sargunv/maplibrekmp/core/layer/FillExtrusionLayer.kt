package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.expressions.Expression as MLNExpression
import org.maplibre.android.style.layers.FillExtrusionLayer as MLNFillExtrusionLayer

@PublishedApi
internal actual class FillExtrusionLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = MLNFillExtrusionLayer(id, source.id)

  override var sourceLayer: String by impl::sourceLayer

  override fun setFilter(filter: Expression<Boolean>) {
    impl.setFilter(filter.toMLNExpression() ?: MLNExpression.literal(true))
  }

  actual fun setFillExtrusionOpacity(opacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillExtrusionOpacity(opacity.toMLNExpression()))
  }

  actual fun setFillExtrusionColor(color: Expression<Color>) {
    impl.setProperties(PropertyFactory.fillExtrusionColor(color.toMLNExpression()))
  }

  actual fun setFillExtrusionTranslate(translate: Expression<Point>) {
    impl.setProperties(PropertyFactory.fillExtrusionTranslate(translate.toMLNExpression()))
  }

  actual fun setFillExtrusionTranslateAnchor(anchor: Expression<String>) {
    impl.setProperties(PropertyFactory.fillExtrusionTranslateAnchor(anchor.toMLNExpression()))
  }

  actual fun setFillExtrusionPattern(pattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.fillExtrusionPattern(pattern.toMLNExpression()))
  }

  actual fun setFillExtrusionHeight(height: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillExtrusionHeight(height.toMLNExpression()))
  }

  actual fun setFillExtrusionBase(base: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillExtrusionBase(base.toMLNExpression()))
  }

  actual fun setFillExtrusionVerticalGradient(verticalGradient: Expression<Boolean>) {
    impl.setProperties(
      PropertyFactory.fillExtrusionVerticalGradient(verticalGradient.toMLNExpression())
    )
  }
}
