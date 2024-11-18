package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.expressions.Expression as MLNExpression
import org.maplibre.android.style.layers.CircleLayer as MLNCircleLayer

@PublishedApi
internal actual class CircleLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = MLNCircleLayer(id, source.id)

  override var sourceLayer: String by impl::sourceLayer

  override fun setFilter(filter: Expression<Boolean>) {
    impl.setFilter(filter.toMLNExpression() ?: MLNExpression.literal(true))
  }

  actual fun setCircleSortKey(sortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleSortKey(sortKey.toMLNExpression()))
  }

  actual fun setCircleRadius(radius: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleRadius(radius.toMLNExpression()))
  }

  actual fun setCircleColor(color: Expression<Color>) {
    impl.setProperties(PropertyFactory.circleColor(color.toMLNExpression()))
  }

  actual fun setCircleBlur(blur: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleBlur(blur.toMLNExpression()))
  }

  actual fun setCircleOpacity(opacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleOpacity(opacity.toMLNExpression()))
  }

  actual fun setCircleTranslate(translate: Expression<Point>) {
    impl.setProperties(PropertyFactory.circleTranslate(translate.toMLNExpression()))
  }

  actual fun setCircleTranslateAnchor(translateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.circleTranslateAnchor(translateAnchor.toMLNExpression()))
  }

  actual fun setCirclePitchScale(pitchScale: Expression<String>) {
    impl.setProperties(PropertyFactory.circlePitchScale(pitchScale.toMLNExpression()))
  }

  actual fun setCirclePitchAlignment(pitchAlignment: Expression<String>) {
    impl.setProperties(PropertyFactory.circlePitchAlignment(pitchAlignment.toMLNExpression()))
  }

  actual fun setCircleStrokeWidth(strokeWidth: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleStrokeWidth(strokeWidth.toMLNExpression()))
  }

  actual fun setCircleStrokeColor(strokeColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.circleStrokeColor(strokeColor.toMLNExpression()))
  }

  actual fun setCircleStrokeOpacity(strokeOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleStrokeOpacity(strokeOpacity.toMLNExpression()))
  }
}
