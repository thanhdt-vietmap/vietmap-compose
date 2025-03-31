package dev.sargunv.vietmapcompose.core.layer

import cocoapods.VietMap.MLNCircleStyleLayer
import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.VietMapcompose.core.util.toNSExpression
import dev.sargunv.VietMapcompose.core.util.toNSPredicate
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import dev.sargunv.vietmapcompose.expressions.value.CirclePitchAlignment
import dev.sargunv.vietmapcompose.expressions.value.CirclePitchScale
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.DpOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.DpValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.TranslateAnchor

internal actual class CircleLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNCircleStyleLayer(id, source.impl)

  actual override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    impl.predicate = filter.toNSPredicate()
  }

  actual fun setCircleSortKey(sortKey: CompiledExpression<FloatValue>) {
    impl.circleSortKey = sortKey.toNSExpression()
  }

  actual fun setCircleRadius(radius: CompiledExpression<DpValue>) {
    impl.circleRadius = radius.toNSExpression()
  }

  actual fun setCircleColor(color: CompiledExpression<ColorValue>) {
    impl.circleColor = color.toNSExpression()
  }

  actual fun setCircleBlur(blur: CompiledExpression<FloatValue>) {
    impl.circleBlur = blur.toNSExpression()
  }

  actual fun setCircleOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.circleOpacity = opacity.toNSExpression()
  }

  actual fun setCircleTranslate(translate: CompiledExpression<DpOffsetValue>) {
    impl.circleTranslation = translate.toNSExpression()
  }

  actual fun setCircleTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    impl.circleTranslationAnchor = translateAnchor.toNSExpression()
  }

  actual fun setCirclePitchScale(pitchScale: CompiledExpression<CirclePitchScale>) {
    impl.circleScaleAlignment = pitchScale.toNSExpression()
  }

  actual fun setCirclePitchAlignment(pitchAlignment: CompiledExpression<CirclePitchAlignment>) {
    impl.circlePitchAlignment = pitchAlignment.toNSExpression()
  }

  actual fun setCircleStrokeWidth(strokeWidth: CompiledExpression<DpValue>) {
    impl.circleStrokeWidth = strokeWidth.toNSExpression()
  }

  actual fun setCircleStrokeColor(strokeColor: CompiledExpression<ColorValue>) {
    impl.circleStrokeColor = strokeColor.toNSExpression()
  }

  actual fun setCircleStrokeOpacity(strokeOpacity: CompiledExpression<FloatValue>) {
    impl.circleStrokeOpacity = strokeOpacity.toNSExpression()
  }
}
