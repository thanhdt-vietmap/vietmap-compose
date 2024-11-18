package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNCircleStyleLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toNSExpression
import dev.sargunv.maplibrekmp.core.util.toPredicate
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point

@PublishedApi
internal actual class CircleLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNCircleStyleLayer(id, source.impl)

  override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  override fun setFilter(filter: Expression<Boolean>) {
    impl.predicate = filter.toPredicate()
  }

  actual fun setCircleSortKey(circleSortKey: Expression<Number>) {
    impl.circleSortKey = circleSortKey.toNSExpression()
  }

  actual fun setCircleRadius(circleRadius: Expression<Number>) {
    impl.circleRadius = circleRadius.toNSExpression()
  }

  actual fun setCircleColor(circleColor: Expression<Color>) {
    impl.circleColor = circleColor.toNSExpression()
  }

  actual fun setCircleBlur(circleBlur: Expression<Number>) {
    impl.circleBlur = circleBlur.toNSExpression()
  }

  actual fun setCircleOpacity(circleOpacity: Expression<Number>) {
    impl.circleOpacity = circleOpacity.toNSExpression()
  }

  actual fun setCircleTranslate(circleTranslate: Expression<Point>) {
    impl.circleTranslation = circleTranslate.toNSExpression()
  }

  actual fun setCircleTranslateAnchor(circleTranslateAnchor: Expression<String>) {
    impl.circleTranslationAnchor = circleTranslateAnchor.toNSExpression()
  }

  actual fun setCirclePitchScale(circlePitchScale: Expression<String>) {
    impl.circleScaleAlignment = circlePitchScale.toNSExpression()
  }

  actual fun setCirclePitchAlignment(circlePitchAlignment: Expression<String>) {
    impl.circlePitchAlignment = circlePitchAlignment.toNSExpression()
  }

  actual fun setCircleStrokeWidth(circleStrokeWidth: Expression<Number>) {
    impl.circleStrokeWidth = circleStrokeWidth.toNSExpression()
  }

  actual fun setCircleStrokeColor(circleStrokeColor: Expression<Color>) {
    impl.circleStrokeColor = circleStrokeColor.toNSExpression()
  }

  actual fun setCircleStrokeOpacity(circleStrokeOpacity: Expression<Number>) {
    impl.circleStrokeOpacity = circleStrokeOpacity.toNSExpression()
  }
}
