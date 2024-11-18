package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.expressions.Expression as MLNExpression
import org.maplibre.android.style.layers.FillLayer as MLNFillLayer

@PublishedApi
internal actual class FillLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = MLNFillLayer(id, source.id)

  override var sourceLayer: String by impl::sourceLayer

  override fun setFilter(filter: Expression<Boolean>) {
    impl.setFilter(filter.toMLNExpression() ?: MLNExpression.literal(true))
  }

  actual fun setFillSortKey(sortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillSortKey(sortKey.toMLNExpression()))
  }

  actual fun setFillAntialias(antialias: Expression<Boolean>) {
    impl.setProperties(PropertyFactory.fillAntialias(antialias.toMLNExpression()))
  }

  actual fun setFillOpacity(opacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillOpacity(opacity.toMLNExpression()))
  }

  actual fun setFillColor(color: Expression<Color>) {
    impl.setProperties(PropertyFactory.fillColor(color.toMLNExpression()))
  }

  actual fun setFillOutlineColor(outlineColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.fillOutlineColor(outlineColor.toMLNExpression()))
  }

  actual fun setFillTranslate(translate: Expression<Point>) {
    impl.setProperties(PropertyFactory.fillTranslate(translate.toMLNExpression()))
  }

  actual fun setFillTranslateAnchor(translateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.fillTranslateAnchor(translateAnchor.toMLNExpression()))
  }

  actual fun setFillPattern(pattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.fillPattern(pattern.toMLNExpression()))
  }
}
