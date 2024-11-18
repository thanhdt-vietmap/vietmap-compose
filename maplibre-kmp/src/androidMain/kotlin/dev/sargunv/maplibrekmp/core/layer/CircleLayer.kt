package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.ExpressionAdapter.convert
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import org.maplibre.android.style.expressions.Expression as MLNExpression
import org.maplibre.android.style.layers.CircleLayer as MLNCircleLayer
import org.maplibre.android.style.layers.PropertyFactory

@PublishedApi
internal actual class CircleLayer actual constructor(id: String, source: Source) :
  UserFeatureLayer(source) {
  override val impl = MLNCircleLayer(id, source.id)

  override var sourceLayer: String by impl::sourceLayer

  override fun setFilter(filter: Expression<Boolean>) {
    impl.setFilter(filter.convert() ?: MLNExpression.literal(true))
  }

  actual fun setCircleSortKey(circleSortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleSortKey(circleSortKey.convert()))
  }

  actual fun setCircleRadius(circleRadius: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleRadius(circleRadius.convert()))
  }

  actual fun setCircleColor(circleColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.circleColor(circleColor.convert()))
  }

  actual fun setCircleBlur(circleBlur: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleBlur(circleBlur.convert()))
  }

  actual fun setCircleOpacity(circleOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleOpacity(circleOpacity.convert()))
  }

  actual fun setCircleTranslate(circleTranslate: Expression<Point>) {
    impl.setProperties(PropertyFactory.circleTranslate(circleTranslate.convert()))
  }

  actual fun setCircleTranslateAnchor(circleTranslateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.circleTranslateAnchor(circleTranslateAnchor.convert()))
  }

  actual fun setCirclePitchScale(circlePitchScale: Expression<String>) {
    impl.setProperties(PropertyFactory.circlePitchScale(circlePitchScale.convert()))
  }

  actual fun setCirclePitchAlignment(circlePitchAlignment: Expression<String>) {
    impl.setProperties(PropertyFactory.circlePitchAlignment(circlePitchAlignment.convert()))
  }

  actual fun setCircleStrokeWidth(circleStrokeWidth: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleStrokeWidth(circleStrokeWidth.convert()))
  }

  actual fun setCircleStrokeColor(circleStrokeColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.circleStrokeColor(circleStrokeColor.convert()))
  }

  actual fun setCircleStrokeOpacity(circleStrokeOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.circleStrokeOpacity(circleStrokeOpacity.convert()))
  }
}
