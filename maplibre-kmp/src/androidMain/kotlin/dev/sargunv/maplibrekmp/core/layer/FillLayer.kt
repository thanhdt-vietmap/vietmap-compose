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

  actual fun setFillSortKey(fillSortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillSortKey(fillSortKey.toMLNExpression()))
  }

  actual fun setFillAntialias(fillAntialias: Expression<Boolean>) {
    impl.setProperties(PropertyFactory.fillAntialias(fillAntialias.toMLNExpression()))
  }

  actual fun setFillOpacity(fillOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillOpacity(fillOpacity.toMLNExpression()))
  }

  actual fun setFillColor(fillColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.fillColor(fillColor.toMLNExpression()))
  }

  actual fun setFillOutlineColor(fillOutlineColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.fillOutlineColor(fillOutlineColor.toMLNExpression()))
  }

  actual fun setFillTranslate(fillTranslate: Expression<Point>) {
    impl.setProperties(PropertyFactory.fillTranslate(fillTranslate.toMLNExpression()))
  }

  actual fun setFillTranslateAnchor(fillTranslateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.fillTranslateAnchor(fillTranslateAnchor.toMLNExpression()))
  }

  actual fun setFillPattern(fillPattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.fillPattern(fillPattern.toMLNExpression()))
  }
}
