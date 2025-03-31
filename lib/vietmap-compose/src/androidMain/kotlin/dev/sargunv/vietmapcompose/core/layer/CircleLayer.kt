package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.core.util.toMLNExpression
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import dev.sargunv.vietmapcompose.expressions.value.CirclePitchAlignment
import dev.sargunv.vietmapcompose.expressions.value.CirclePitchScale
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.DpOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.DpValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.TranslateAnchor
import vn.vietmap.vietmapsdk.style.expressions.Expression as MLNExpression
import vn.vietmap.vietmapsdk.style.layers.CircleLayer as MLNCircleLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory

internal actual class CircleLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = MLNCircleLayer(id, source.id)

  actual override var sourceLayer: String by impl::sourceLayer

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    impl.setFilter(filter.toMLNExpression() ?: MLNExpression.literal(true))
  }

  actual fun setCircleSortKey(sortKey: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.circleSortKey(sortKey.toMLNExpression()))
  }

  actual fun setCircleRadius(radius: CompiledExpression<DpValue>) {
    impl.setProperties(PropertyFactory.circleRadius(radius.toMLNExpression()))
  }

  actual fun setCircleColor(color: CompiledExpression<ColorValue>) {
    impl.setProperties(PropertyFactory.circleColor(color.toMLNExpression()))
  }

  actual fun setCircleBlur(blur: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.circleBlur(blur.toMLNExpression()))
  }

  actual fun setCircleOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.circleOpacity(opacity.toMLNExpression()))
  }

  actual fun setCircleTranslate(translate: CompiledExpression<DpOffsetValue>) {
    impl.setProperties(PropertyFactory.circleTranslate(translate.toMLNExpression()))
  }

  actual fun setCircleTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    impl.setProperties(PropertyFactory.circleTranslateAnchor(translateAnchor.toMLNExpression()))
  }

  actual fun setCirclePitchScale(pitchScale: CompiledExpression<CirclePitchScale>) {
    impl.setProperties(PropertyFactory.circlePitchScale(pitchScale.toMLNExpression()))
  }

  actual fun setCirclePitchAlignment(pitchAlignment: CompiledExpression<CirclePitchAlignment>) {
    impl.setProperties(PropertyFactory.circlePitchAlignment(pitchAlignment.toMLNExpression()))
  }

  actual fun setCircleStrokeWidth(strokeWidth: CompiledExpression<DpValue>) {
    impl.setProperties(PropertyFactory.circleStrokeWidth(strokeWidth.toMLNExpression()))
  }

  actual fun setCircleStrokeColor(strokeColor: CompiledExpression<ColorValue>) {
    impl.setProperties(PropertyFactory.circleStrokeColor(strokeColor.toMLNExpression()))
  }

  actual fun setCircleStrokeOpacity(strokeOpacity: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.circleStrokeOpacity(strokeOpacity.toMLNExpression()))
  }
}
