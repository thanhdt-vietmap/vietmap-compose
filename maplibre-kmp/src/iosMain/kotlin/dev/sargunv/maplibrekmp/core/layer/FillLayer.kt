package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNFillStyleLayer
import dev.sargunv.maplibrekmp.core.layer.ExpressionAdapter.toNSExpression
import dev.sargunv.maplibrekmp.core.layer.ExpressionAdapter.toPredicate
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal actual class FillLayer actual constructor(id: String, source: Source, anchor: Anchor) :
  UserFeatureLayer(source, anchor) {

  override val impl = MLNFillStyleLayer(id, source.impl)

  override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  override fun setFilter(filter: Expression<Boolean>) {
    impl.predicate = filter.toPredicate()
  }

  actual fun setFillSortKey(fillSortKey: Expression<Number>) {
    impl.fillSortKey = fillSortKey.toNSExpression()
  }

  actual fun setFillAntialias(fillAntialias: Expression<Boolean>) {
    impl.fillAntialiased = fillAntialias.toNSExpression()
  }

  actual fun setFillOpacity(fillOpacity: Expression<Number>) {
    impl.fillOpacity = fillOpacity.toNSExpression()
  }

  actual fun setFillColor(fillColor: Expression<Color>) {
    impl.fillColor = fillColor.toNSExpression()
  }

  actual fun setFillOutlineColor(fillOutlineColor: Expression<Color>) {
    impl.fillOutlineColor = fillOutlineColor.toNSExpression()
  }

  actual fun setFillTranslate(fillTranslate: Expression<Point>) {
    impl.fillTranslation = fillTranslate.toNSExpression()
  }

  actual fun setFillTranslateAnchor(fillTranslateAnchor: Expression<String>) {
    impl.fillTranslationAnchor = fillTranslateAnchor.toNSExpression()
  }

  actual fun setFillPattern(fillPattern: Expression<TResolvedImage>) {
    // TODO: figure out how to unset a pattern in iOS
    if (fillPattern.value != null) {
      impl.fillPattern = fillPattern.toNSExpression()
    }
  }
}
