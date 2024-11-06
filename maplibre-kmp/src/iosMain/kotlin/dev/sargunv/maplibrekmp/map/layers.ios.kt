package dev.sargunv.maplibrekmp.map

import cocoapods.MapLibre.MLNLineStyleLayer
import cocoapods.MapLibre.MLNSource
import cocoapods.MapLibre.MLNStyleLayer
import dev.sargunv.maplibrekmp.style.Layer

internal fun Layer.Type.Line.toNativeLayer(id: String, source: MLNSource): MLNLineStyleLayer {
  return MLNLineStyleLayer(id, source).apply {
    cap?.let { setLineCap(it.toNSExpression()) }
    join?.let { setLineJoin(it.toNSExpression()) }
    color?.let { setLineColor(it.toNSExpression()) }
    width?.let { setLineWidth(it.toNSExpression()) }
  }
}

internal fun MLNStyleLayer.applyLayerOptions(layer: Layer) {
  layer.minZoom?.let { setMinimumZoomLevel(it) }
  layer.maxZoom?.let { setMaximumZoomLevel(it) }
}

internal fun Layer.toNativeLayer(getSource: (id: String) -> MLNSource): MLNStyleLayer {
  return when (this.type) {
    is Layer.Type.Line -> type.toNativeLayer(id, getSource(source.id))
  }.also { it.applyLayerOptions(this) }
}
