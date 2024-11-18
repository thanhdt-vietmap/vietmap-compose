package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.expressions.Expression as MLNExpression
import org.maplibre.android.style.layers.LineLayer as MLNLineLayer

@PublishedApi
internal actual class LineLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNLineLayer(id, source.id)

  override var sourceLayer: String by impl::sourceLayer

  override fun setFilter(filter: Expression<Boolean>) {
    impl.setFilter(filter.toMLNExpression() ?: MLNExpression.literal(true))
  }

  actual fun setLineCap(lineCap: Expression<String>) {
    impl.setProperties(PropertyFactory.lineCap(lineCap.toMLNExpression()))
  }

  actual fun setLineJoin(lineJoin: Expression<String>) {
    impl.setProperties(PropertyFactory.lineJoin(lineJoin.toMLNExpression()))
  }

  actual fun setLineMiterLimit(lineMiterLimit: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineMiterLimit(lineMiterLimit.toMLNExpression()))
  }

  actual fun setLineRoundLimit(lineRoundLimit: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineRoundLimit(lineRoundLimit.toMLNExpression()))
  }

  actual fun setLineSortKey(lineSortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineSortKey(lineSortKey.toMLNExpression()))
  }

  actual fun setLineOpacity(lineOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineOpacity(lineOpacity.toMLNExpression()))
  }

  actual fun setLineColor(lineColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.lineColor(lineColor.toMLNExpression()))
  }

  actual fun setLineTranslate(lineTranslate: Expression<Point>) {
    impl.setProperties(PropertyFactory.lineTranslate(lineTranslate.toMLNExpression()))
  }

  actual fun setLineTranslateAnchor(lineTranslateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.lineTranslateAnchor(lineTranslateAnchor.toMLNExpression()))
  }

  actual fun setLineWidth(lineWidth: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineWidth(lineWidth.toMLNExpression()))
  }

  actual fun setLineGapWidth(lineGapWidth: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineGapWidth(lineGapWidth.toMLNExpression()))
  }

  actual fun setLineOffset(lineOffset: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineOffset(lineOffset.toMLNExpression()))
  }

  actual fun setLineBlur(lineBlur: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineBlur(lineBlur.toMLNExpression()))
  }

  actual fun setLineDasharray(lineDasharray: Expression<List<Number>>) {
    impl.setProperties(PropertyFactory.lineDasharray(lineDasharray.toMLNExpression()))
  }

  actual fun setLinePattern(linePattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.linePattern(linePattern.toMLNExpression()))
  }

  actual fun setLineGradient(lineGradient: Expression<Color>) {
    impl.setProperties(PropertyFactory.lineGradient(lineGradient.toMLNExpression()))
  }
}
