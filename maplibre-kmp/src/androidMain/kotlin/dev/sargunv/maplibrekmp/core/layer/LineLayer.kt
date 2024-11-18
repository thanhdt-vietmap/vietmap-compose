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

  actual fun setLineCap(cap: Expression<String>) {
    impl.setProperties(PropertyFactory.lineCap(cap.toMLNExpression()))
  }

  actual fun setLineJoin(join: Expression<String>) {
    impl.setProperties(PropertyFactory.lineJoin(join.toMLNExpression()))
  }

  actual fun setLineMiterLimit(miterLimit: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineMiterLimit(miterLimit.toMLNExpression()))
  }

  actual fun setLineRoundLimit(roundLimit: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineRoundLimit(roundLimit.toMLNExpression()))
  }

  actual fun setLineSortKey(sortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineSortKey(sortKey.toMLNExpression()))
  }

  actual fun setLineOpacity(opacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineOpacity(opacity.toMLNExpression()))
  }

  actual fun setLineColor(color: Expression<Color>) {
    impl.setProperties(PropertyFactory.lineColor(color.toMLNExpression()))
  }

  actual fun setLineTranslate(translate: Expression<Point>) {
    impl.setProperties(PropertyFactory.lineTranslate(translate.toMLNExpression()))
  }

  actual fun setLineTranslateAnchor(translateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.lineTranslateAnchor(translateAnchor.toMLNExpression()))
  }

  actual fun setLineWidth(width: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineWidth(width.toMLNExpression()))
  }

  actual fun setLineGapWidth(gapWidth: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineGapWidth(gapWidth.toMLNExpression()))
  }

  actual fun setLineOffset(offset: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineOffset(offset.toMLNExpression()))
  }

  actual fun setLineBlur(blur: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineBlur(blur.toMLNExpression()))
  }

  actual fun setLineDasharray(dasharray: Expression<List<Number>>) {
    impl.setProperties(PropertyFactory.lineDasharray(dasharray.toMLNExpression()))
  }

  actual fun setLinePattern(pattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.linePattern(pattern.toMLNExpression()))
  }

  actual fun setLineGradient(gradient: Expression<Color>) {
    impl.setProperties(PropertyFactory.lineGradient(gradient.toMLNExpression()))
  }
}
