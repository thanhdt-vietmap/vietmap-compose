package dev.sargunv.maplibrekmp.map

import dev.sargunv.maplibrekmp.map.NativeExpressionAdapter.convert as convertExpr
import dev.sargunv.maplibrekmp.style.Layer
import org.maplibre.android.style.layers.Layer as NativeLayer
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.PropertyFactory

internal object NativeLayerAdapter : StyleManager.Adapter<Layer, NativeLayer> {
  private fun Layer.Type.Line.buildNativeLayer(id: String, sourceId: String): LineLayer {
    return LineLayer(id, sourceId).apply {
      withProperties(
        cap?.let { PropertyFactory.lineCap(convertExpr(it)) },
        join?.let { PropertyFactory.lineJoin(convertExpr(it)) },
        color?.let { PropertyFactory.lineColor(convertExpr(it)) },
        width?.let { PropertyFactory.lineWidth(convertExpr(it)) },
      )
    }
  }

  private fun NativeLayer.finalizeNativeLayer(layer: Layer) {
    layer.minZoom?.let { minZoom = it }
    layer.maxZoom?.let { maxZoom = it }
  }

  override fun convert(common: Layer): NativeLayer {
    return when (common.type) {
      is Layer.Type.Line -> common.type.buildNativeLayer(common.id, common.source.id)
    }.also { it.finalizeNativeLayer(common) }
  }
}
