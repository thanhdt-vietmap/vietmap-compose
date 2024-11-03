package dev.sargunv.maplibrecompose

import org.maplibre.android.style.expressions.Expression
import org.maplibre.android.style.layers.Layer as NativeLayer
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.PropertyFactory

internal fun NativeLayer.applyLayerOptions(layer: Layer) {
  layer.minZoom?.let { minZoom = it }
  layer.maxZoom?.let { maxZoom = it }
}

internal fun Layer.Type.Line.toNativeLayer(layer: Layer): LineLayer {
  return LineLayer(layer.id, layer.source).apply {
    applyLayerOptions(layer)
    withProperties(
      cap?.let { PropertyFactory.lineCap(it) },
      join?.let { PropertyFactory.lineJoin(it) },
      color?.let {
        PropertyFactory.lineColor(
          Expression.rgba(it.red.toShort(), it.green.toShort(), it.blue.toShort(), it.alpha)
        )
      },
      //      width?.let { PropertyFactory.lineWidth(it) },
      PropertyFactory.lineWidth(fancyWidth()),
    )
  }
}
