package dev.sargunv.maplibrekmp.map

import cocoapods.MapLibre.MLNLineStyleLayer
import cocoapods.MapLibre.MLNStyle
import cocoapods.MapLibre.MLNStyleLayer
import dev.sargunv.maplibrekmp.map.NativeExpressionAdapter.convert as convertExpr
import dev.sargunv.maplibrekmp.style.Layer

internal class NativeLayerAdapter(private val style: MLNStyle) :
  StyleManager.Adapter<Layer, MLNStyleLayer> {
  private fun getSource(id: String) =
    style.sourceWithIdentifier(id) ?: error("Source not found: $id")

  private fun Layer.Type.Line.buildNativeLayer(id: String, sourceId: String): MLNLineStyleLayer {
    return MLNLineStyleLayer(id, getSource(sourceId)).apply {
      cap?.let { setLineCap(convertExpr(it)) }
      join?.let { setLineJoin(convertExpr(it)) }
      color?.let { setLineColor(convertExpr(it)) }
      width?.let { setLineWidth(convertExpr(it)) }
    }
  }

  private fun MLNStyleLayer.finalizeNativeLayer(layer: Layer) {
    layer.minZoom?.let { setMinimumZoomLevel(it) }
    layer.maxZoom?.let { setMaximumZoomLevel(it) }
  }

  override fun convert(common: Layer): MLNStyleLayer {
    return when (common.type) {
      is Layer.Type.Line -> common.type.buildNativeLayer(common.id, common.source.id)
    }.also { it.finalizeNativeLayer(common) }
  }
}
