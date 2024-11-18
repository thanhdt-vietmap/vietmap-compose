package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNFillStyleLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toNSExpression
import dev.sargunv.maplibrekmp.core.util.toPredicate
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal actual class FillLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNFillStyleLayer(id, source.impl)

  override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  override fun setFilter(filter: Expression<Boolean>) {
    impl.predicate = filter.toPredicate()
  }

  actual fun setFillSortKey(sortKey: Expression<Number>) {
    impl.fillSortKey = sortKey.toNSExpression()
  }

  actual fun setFillAntialias(antialias: Expression<Boolean>) {
    impl.fillAntialiased = antialias.toNSExpression()
  }

  actual fun setFillOpacity(opacity: Expression<Number>) {
    impl.fillOpacity = opacity.toNSExpression()
  }

  actual fun setFillColor(color: Expression<Color>) {
    impl.fillColor = color.toNSExpression()
  }

  actual fun setFillOutlineColor(outlineColor: Expression<Color>) {
    impl.fillOutlineColor = outlineColor.toNSExpression()
  }

  actual fun setFillTranslate(translate: Expression<Point>) {
    impl.fillTranslation = translate.toNSExpression()
  }

  actual fun setFillTranslateAnchor(translateAnchor: Expression<String>) {
    impl.fillTranslationAnchor = translateAnchor.toNSExpression()
  }

  actual fun setFillPattern(pattern: Expression<TResolvedImage>) {
    // TODO: figure out how to unset a pattern in iOS
    if (pattern.value != null) {
      impl.fillPattern = pattern.toNSExpression()
    }
  }
}
