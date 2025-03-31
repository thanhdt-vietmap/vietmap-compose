package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.core.util.toMLNExpression
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.DpOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.DpValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.ImageValue
import dev.sargunv.vietmapcompose.expressions.value.LineCap
import dev.sargunv.vietmapcompose.expressions.value.LineJoin
import dev.sargunv.vietmapcompose.expressions.value.TranslateAnchor
import dev.sargunv.vietmapcompose.expressions.value.VectorValue
import vn.vietmap.vietmapsdk.style.expressions.Expression as MLNExpression
import vn.vietmap.vietmapsdk.style.layers.LineLayer as MLNLineLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory

internal actual class LineLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNLineLayer(id, source.id)

  actual override var sourceLayer: String by impl::sourceLayer

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    impl.setFilter(filter.toMLNExpression() ?: MLNExpression.literal(true))
  }

  actual fun setLineCap(cap: CompiledExpression<LineCap>) {
    impl.setProperties(PropertyFactory.lineCap(cap.toMLNExpression()))
  }

  actual fun setLineJoin(join: CompiledExpression<LineJoin>) {
    impl.setProperties(PropertyFactory.lineJoin(join.toMLNExpression()))
  }

  actual fun setLineMiterLimit(miterLimit: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.lineMiterLimit(miterLimit.toMLNExpression()))
  }

  actual fun setLineRoundLimit(roundLimit: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.lineRoundLimit(roundLimit.toMLNExpression()))
  }

  actual fun setLineSortKey(sortKey: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.lineSortKey(sortKey.toMLNExpression()))
  }

  actual fun setLineOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.lineOpacity(opacity.toMLNExpression()))
  }

  actual fun setLineColor(color: CompiledExpression<ColorValue>) {
    impl.setProperties(PropertyFactory.lineColor(color.toMLNExpression()))
  }

  actual fun setLineTranslate(translate: CompiledExpression<DpOffsetValue>) {
    impl.setProperties(PropertyFactory.lineTranslate(translate.toMLNExpression()))
  }

  actual fun setLineTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    impl.setProperties(PropertyFactory.lineTranslateAnchor(translateAnchor.toMLNExpression()))
  }

  actual fun setLineWidth(width: CompiledExpression<DpValue>) {
    impl.setProperties(PropertyFactory.lineWidth(width.toMLNExpression()))
  }

  actual fun setLineGapWidth(gapWidth: CompiledExpression<DpValue>) {
    impl.setProperties(PropertyFactory.lineGapWidth(gapWidth.toMLNExpression()))
  }

  actual fun setLineOffset(offset: CompiledExpression<DpValue>) {
    impl.setProperties(PropertyFactory.lineOffset(offset.toMLNExpression()))
  }

  actual fun setLineBlur(blur: CompiledExpression<DpValue>) {
    impl.setProperties(PropertyFactory.lineBlur(blur.toMLNExpression()))
  }

  actual fun setLineDasharray(dasharray: CompiledExpression<VectorValue<Number>>) {
    impl.setProperties(PropertyFactory.lineDasharray(dasharray.toMLNExpression()))
  }

  actual fun setLinePattern(pattern: CompiledExpression<ImageValue>) {
    impl.setProperties(PropertyFactory.linePattern(pattern.toMLNExpression()))
  }

  actual fun setLineGradient(gradient: CompiledExpression<ColorValue>) {
    impl.setProperties(PropertyFactory.lineGradient(gradient.toMLNExpression()))
  }
}
