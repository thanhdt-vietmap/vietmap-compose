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

  actual fun setCircleSortKey(circleSortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleSortKey(circleSortKey.toMLNExpression()))
  }

  actual fun setCircleRadius(circleRadius: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleRadius(circleRadius.toMLNExpression()))
  }

  actual fun setCircleColor(circleColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.circleColor(circleColor.toMLNExpression()))
  }

  actual fun setCircleBlur(circleBlur: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleBlur(circleBlur.toMLNExpression()))
  }

  actual fun setCircleOpacity(circleOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleOpacity(circleOpacity.toMLNExpression()))
  }

  actual fun setCircleTranslate(circleTranslate: Expression<Point>) {
    impl.setProperties(PropertyFactory.circleTranslate(circleTranslate.toMLNExpression()))
  }

  actual fun setCircleTranslateAnchor(circleTranslateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.circleTranslateAnchor(circleTranslateAnchor.toMLNExpression()))
  }

  actual fun setCirclePitchScale(circlePitchScale: Expression<String>) {
    impl.setProperties(PropertyFactory.circlePitchScale(circlePitchScale.toMLNExpression()))
  }

  actual fun setCirclePitchAlignment(circlePitchAlignment: Expression<String>) {
    impl.setProperties(PropertyFactory.circlePitchAlignment(circlePitchAlignment.toMLNExpression()))
  }

  actual fun setCircleStrokeWidth(circleStrokeWidth: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleStrokeWidth(circleStrokeWidth.toMLNExpression()))
  }

  actual fun setCircleStrokeColor(circleStrokeColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.circleStrokeColor(circleStrokeColor.toMLNExpression()))
  }

  actual fun setCircleStrokeOpacity(circleStrokeOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleStrokeOpacity(circleStrokeOpacity.toMLNExpression()))
  }
}
