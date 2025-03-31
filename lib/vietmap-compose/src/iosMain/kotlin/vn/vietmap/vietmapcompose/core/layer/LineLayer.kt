package vn.vietmap.vietmapcompose.core.layer

import cocoapods.VietMap.MLNLineStyleLayer
import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.VietMapcompose.core.util.toNSExpression
import vn.vietmap.VietMapcompose.core.util.toNSPredicate
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.ast.NullLiteral
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpOffsetValue
import vn.vietmap.vietmapcompose.expressions.value.DpValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.ImageValue
import vn.vietmap.vietmapcompose.expressions.value.LineCap
import vn.vietmap.vietmapcompose.expressions.value.LineJoin
import vn.vietmap.vietmapcompose.expressions.value.TranslateAnchor
import vn.vietmap.vietmapcompose.expressions.value.VectorValue

internal actual class LineLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNLineStyleLayer(id, source.impl)

  actual override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    impl.predicate = filter.toNSPredicate()
  }

  actual fun setLineCap(cap: CompiledExpression<LineCap>) {
    impl.lineCap = cap.toNSExpression()
  }

  actual fun setLineJoin(join: CompiledExpression<LineJoin>) {
    impl.lineJoin = join.toNSExpression()
  }

  actual fun setLineMiterLimit(miterLimit: CompiledExpression<FloatValue>) {
    impl.lineMiterLimit = miterLimit.toNSExpression()
  }

  actual fun setLineRoundLimit(roundLimit: CompiledExpression<FloatValue>) {
    impl.lineRoundLimit = roundLimit.toNSExpression()
  }

  actual fun setLineSortKey(sortKey: CompiledExpression<FloatValue>) {
    impl.lineSortKey = sortKey.toNSExpression()
  }

  actual fun setLineOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.lineOpacity = opacity.toNSExpression()
  }

  actual fun setLineColor(color: CompiledExpression<ColorValue>) {
    impl.lineColor = color.toNSExpression()
  }

  actual fun setLineTranslate(translate: CompiledExpression<DpOffsetValue>) {
    impl.lineTranslation = translate.toNSExpression()
  }

  actual fun setLineTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    impl.lineTranslationAnchor = translateAnchor.toNSExpression()
  }

  actual fun setLineWidth(width: CompiledExpression<DpValue>) {
    impl.lineWidth = width.toNSExpression()
  }

  actual fun setLineGapWidth(gapWidth: CompiledExpression<DpValue>) {
    impl.lineGapWidth = gapWidth.toNSExpression()
  }

  actual fun setLineOffset(offset: CompiledExpression<DpValue>) {
    impl.lineOffset = offset.toNSExpression()
  }

  actual fun setLineBlur(blur: CompiledExpression<DpValue>) {
    impl.lineBlur = blur.toNSExpression()
  }

  actual fun setLineDasharray(dasharray: CompiledExpression<VectorValue<Number>>) {
    impl.lineDashPattern = dasharray.toNSExpression()
  }

  actual fun setLinePattern(pattern: CompiledExpression<ImageValue>) {
    // TODO: figure out how to unset a pattern in iOS
    if (pattern != NullLiteral) {
      impl.linePattern = pattern.toNSExpression()
    }
  }

  actual fun setLineGradient(gradient: CompiledExpression<ColorValue>) {
    impl.lineGradient = gradient.toNSExpression()
  }
}
