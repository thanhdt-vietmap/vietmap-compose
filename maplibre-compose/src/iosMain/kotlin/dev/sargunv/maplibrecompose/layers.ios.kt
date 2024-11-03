package dev.sargunv.maplibrecompose

import cocoapods.MapLibre.MLNLineStyleLayer
import cocoapods.MapLibre.MLNSource
import cocoapods.MapLibre.MLNStyleLayer
import platform.Foundation.NSExpression

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
    cap?.let { setLineCap(NSExpression.expressionForConstantValue(it)) }
    join?.let { setLineJoin(NSExpression.expressionForConstantValue(it)) }
    color?.let { setLineColor(NSExpression.expressionForConstantValue(it.toUiColor())) }
    width?.let { setLineWidth(NSExpression.expressionForConstantValue(it)) }
  }
}
