package dev.sargunv.maplibrecompose

import cocoapods.MapLibre.MLNLineStyleLayer
import cocoapods.MapLibre.MLNSource
import cocoapods.MapLibre.MLNStyleLayer

internal fun MLNStyleLayer.applyLayerOptions(layer: Layer) {
  layer.minZoom?.let { setMinimumZoomLevel(it) }
  layer.maxZoom?.let { setMaximumZoomLevel(it) }
}

internal fun Layer.Type.Line.toNativeLayer(
  getSource: (String) -> MLNSource,
  layer: Layer,
): MLNLineStyleLayer {
  return MLNLineStyleLayer(layer.id, getSource(layer.source)).apply {
    applyLayerOptions(layer)
    cap?.let { setLineCap(it.toNSExpression()) }
    join?.let { setLineJoin(it.toNSExpression()) }
    color?.let { setLineColor(it.toNSExpression()) }
    width?.let { setLineWidth(it.toNSExpression()) }
  }
}
