package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.ExpressionAdapter.convert
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.expressions.Expression as MLNExpression
import org.maplibre.android.style.layers.FillLayer as MLNFillLayer

@PublishedApi
internal actual class FillLayer actual constructor(id: String, source: Source, anchor: Anchor) :
  UserFeatureLayer(source, anchor) {
  override val impl = MLNFillLayer(id, source.id)

  override var sourceLayer: String by impl::sourceLayer

  override fun setFilter(filter: Expression<Boolean>) {
    impl.setFilter(filter.convert() ?: MLNExpression.literal(true))
  }

  actual fun setFillSortKey(fillSortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillSortKey(fillSortKey.convert()))
  }

  actual fun setFillAntialias(fillAntialias: Expression<Boolean>) {
    impl.setProperties(PropertyFactory.fillAntialias(fillAntialias.convert()))
  }

  actual fun setFillOpacity(fillOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.fillOpacity(fillOpacity.convert()))
  }

  actual fun setFillColor(fillColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.fillColor(fillColor.convert()))
  }

  actual fun setFillOutlineColor(fillOutlineColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.fillOutlineColor(fillOutlineColor.convert()))
  }

  actual fun setFillTranslate(fillTranslate: Expression<Point>) {
    impl.setProperties(PropertyFactory.fillTranslate(fillTranslate.convert()))
  }

  actual fun setFillTranslateAnchor(fillTranslateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.fillTranslateAnchor(fillTranslateAnchor.convert()))
  }

  actual fun setFillPattern(fillPattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.fillPattern(fillPattern.convert()))
  }
}
