package dev.sargunv.maplibrekmp.internal.wrapper.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.internal.wrapper.layer.ExpressionAdapter.convert
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Point
import dev.sargunv.maplibrekmp.style.expression.TResolvedImage
import org.maplibre.android.style.layers.LineLayer as MLNLineLayer
import org.maplibre.android.style.layers.Property
import org.maplibre.android.style.layers.PropertyFactory

internal actual class LineLayer
actual constructor(override val id: String, override val source: Source) : Layer() {

  override val impl = MLNLineLayer(id, source.id)

  actual var sourceLayer: String
    get() = impl.sourceLayer
    set(value) {
      impl.sourceLayer = value
    }

  actual var minZoom: Float
    get() = impl.minZoom
    set(value) {
      impl.minZoom = value
    }

  actual var maxZoom: Float
    get() = impl.maxZoom
    set(value) {
      impl.maxZoom = value
    }

  actual fun setFilter(filter: Expression<Boolean>) {
    impl.setFilter(filter.convert())
  }

  actual fun setVisible(value: Boolean) {
    impl.setProperties(PropertyFactory.visibility(if (value) Property.VISIBLE else Property.NONE))
  }

  actual fun setLineCap(lineCap: Expression<String>) {
    impl.setProperties(PropertyFactory.lineCap(lineCap.convert()))
  }

  actual fun setLineJoin(lineJoin: Expression<String>) {
    impl.setProperties(PropertyFactory.lineJoin(lineJoin.convert()))
  }

  actual fun setLineMiterLimit(lineMiterLimit: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineMiterLimit(lineMiterLimit.convert()))
  }

  actual fun setLineRoundLimit(lineRoundLimit: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineRoundLimit(lineRoundLimit.convert()))
  }

  actual fun setLineSortKey(lineSortKey: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineSortKey(lineSortKey.convert()))
  }

  actual fun setLineOpacity(lineOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineOpacity(lineOpacity.convert()))
  }

  actual fun setLineColor(lineColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.lineColor(lineColor.convert()))
  }

  actual fun setLineTranslate(lineTranslate: Expression<Point>) {
    impl.setProperties(PropertyFactory.lineTranslate(lineTranslate.convert()))
  }

  actual fun setLineTranslateAnchor(lineTranslateAnchor: Expression<String>) {
    impl.setProperties(PropertyFactory.lineTranslateAnchor(lineTranslateAnchor.convert()))
  }

  actual fun setLineWidth(lineWidth: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineWidth(lineWidth.convert()))
  }

  actual fun setLineGapWidth(lineGapWidth: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineGapWidth(lineGapWidth.convert()))
  }

  actual fun setLineOffset(lineOffset: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineOffset(lineOffset.convert()))
  }

  actual fun setLineBlur(lineBlur: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineBlur(lineBlur.convert()))
  }

  actual fun setLineDasharray(lineDasharray: Expression<List<Number>>) {
    impl.setProperties(PropertyFactory.lineDasharray(lineDasharray.convert()))
  }

  actual fun setLinePattern(linePattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.linePattern(linePattern.convert()))
  }

  actual fun setLineGradient(lineGradient: Expression<Color>) {
    impl.setProperties(PropertyFactory.lineGradient(lineGradient.convert()))
  }
}
