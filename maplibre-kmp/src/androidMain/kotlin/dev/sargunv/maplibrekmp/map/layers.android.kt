package dev.sargunv.maplibrekmp.map

import dev.sargunv.maplibrekmp.style.Layer
import org.maplibre.android.style.layers.Layer as NativeLayer
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.PropertyFactory

internal fun Layer.Type.Line.toNativeLayer(id: String, sourceId: String): LineLayer {
  return LineLayer(id, sourceId).apply {
    withProperties(
      cap?.let { PropertyFactory.lineCap(it.toMlnExpression()) },
      join?.let { PropertyFactory.lineJoin(it.toMlnExpression()) },
      color?.let { PropertyFactory.lineColor(it.toMlnExpression()) },
      width?.let { PropertyFactory.lineWidth(it.toMlnExpression()) },
    )
  }
}

internal fun NativeLayer.applyLayerOptions(layer: Layer) {
  layer.minZoom?.let { minZoom = it }
  layer.maxZoom?.let { maxZoom = it }
}

internal fun Layer.toNativeLayer(): NativeLayer {
  return when (this.type) {
    is Layer.Type.Line -> type.toNativeLayer(id, source.id)
  }.also { it.applyLayerOptions(this) }
}
