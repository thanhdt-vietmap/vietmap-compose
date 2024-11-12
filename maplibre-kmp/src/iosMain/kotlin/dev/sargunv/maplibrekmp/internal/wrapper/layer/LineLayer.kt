package dev.sargunv.maplibrekmp.internal.wrapper.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNLineStyleLayer
import dev.sargunv.maplibrekmp.internal.wrapper.layer.ExpressionAdapter.toNSExpression
import dev.sargunv.maplibrekmp.internal.wrapper.layer.ExpressionAdapter.toPredicate
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Point
import dev.sargunv.maplibrekmp.style.expression.TResolvedImage

@PublishedApi
internal actual class LineLayer
actual constructor(override val id: String, override val source: Source) : Layer() {

  override val impl = MLNLineStyleLayer(id, source.impl)

  actual var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  actual fun setFilter(filter: Expression<Boolean>) {
    impl.predicate = filter.toPredicate()
  }

  actual fun setLineCap(lineCap: Expression<String>) {
    impl.lineCap = lineCap.toNSExpression()
  }

  actual fun setLineJoin(lineJoin: Expression<String>) {
    impl.lineJoin = lineJoin.toNSExpression()
  }

  actual fun setLineMiterLimit(lineMiterLimit: Expression<Number>) {
    impl.lineMiterLimit = lineMiterLimit.toNSExpression()
  }

  actual fun setLineRoundLimit(lineRoundLimit: Expression<Number>) {
    impl.lineRoundLimit = lineRoundLimit.toNSExpression()
  }

  actual fun setLineSortKey(lineSortKey: Expression<Number>) {
    impl.lineSortKey = lineSortKey.toNSExpression()
  }

  actual fun setLineOpacity(lineOpacity: Expression<Number>) {
    impl.lineOpacity = lineOpacity.toNSExpression()
  }

  actual fun setLineColor(lineColor: Expression<Color>) {
    impl.lineColor = lineColor.toNSExpression()
  }

  actual fun setLineTranslate(lineTranslate: Expression<Point>) {
    impl.lineTranslation = lineTranslate.toNSExpression()
  }

  actual fun setLineTranslateAnchor(lineTranslateAnchor: Expression<String>) {
    impl.lineTranslationAnchor = lineTranslateAnchor.toNSExpression()
  }

  actual fun setLineWidth(lineWidth: Expression<Number>) {
    impl.lineWidth = lineWidth.toNSExpression()
  }

  actual fun setLineGapWidth(lineGapWidth: Expression<Number>) {
    impl.lineGapWidth = lineGapWidth.toNSExpression()
  }

  actual fun setLineOffset(lineOffset: Expression<Number>) {
    impl.lineOffset = lineOffset.toNSExpression()
  }

  actual fun setLineBlur(lineBlur: Expression<Number>) {
    impl.lineBlur = lineBlur.toNSExpression()
  }

  actual fun setLineDasharray(lineDasharray: Expression<List<Number>>) {
    impl.lineDashPattern = lineDasharray.toNSExpression()
  }

  actual fun setLinePattern(linePattern: Expression<TResolvedImage>) {
    // TODO: figure out how to unset a line pattern in iOS
    if (linePattern.value != null) {
      impl.linePattern = linePattern.toNSExpression()
    }
  }

  actual fun setLineGradient(lineGradient: Expression<Color>) {
    impl.lineGradient = lineGradient.toNSExpression()
  }
}
