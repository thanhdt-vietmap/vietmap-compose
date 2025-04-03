package vn.vietmap.vietmapcompose.core.layer

import cocoapods.VietMap.MLNFillExtrusionStyleLayer
import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.core.util.toNSExpression
import vn.vietmap.vietmapcompose.core.util.toNSPredicate
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.ast.NullLiteral
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpOffsetValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.ImageValue
import vn.vietmap.vietmapcompose.expressions.value.TranslateAnchor

internal actual class FillExtrusionLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNFillExtrusionStyleLayer(id, source.impl)

  actual override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    impl.predicate = filter.toNSPredicate()
  }

  actual fun setFillExtrusionOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.fillExtrusionOpacity = opacity.toNSExpression()
  }

  actual fun setFillExtrusionColor(color: CompiledExpression<ColorValue>) {
    impl.fillExtrusionColor = color.toNSExpression()
  }

  actual fun setFillExtrusionTranslate(translate: CompiledExpression<DpOffsetValue>) {
    impl.fillExtrusionTranslation = translate.toNSExpression()
  }

  actual fun setFillExtrusionTranslateAnchor(anchor: CompiledExpression<TranslateAnchor>) {
    impl.fillExtrusionTranslationAnchor = anchor.toNSExpression()
  }

  actual fun setFillExtrusionPattern(pattern: CompiledExpression<ImageValue>) {
    // TODO figure out how to unset pattern
    if (pattern != NullLiteral) impl.fillExtrusionPattern = pattern.toNSExpression()
  }

  actual fun setFillExtrusionHeight(height: CompiledExpression<FloatValue>) {
    impl.fillExtrusionHeight = height.toNSExpression()
  }

  actual fun setFillExtrusionBase(base: CompiledExpression<FloatValue>) {
    impl.fillExtrusionBase = base.toNSExpression()
  }

  actual fun setFillExtrusionVerticalGradient(verticalGradient: CompiledExpression<BooleanValue>) {
    impl.fillExtrusionHasVerticalGradient = verticalGradient.toNSExpression()
  }
}
