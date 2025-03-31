package vn.vietmap.vietmapcompose.core

import androidx.compose.ui.graphics.ImageBitmap
import vn.vietmap.vietmapcompose.core.layer.Layer
import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.maplibrejs.Map as MlJsMap

internal class JsStyle(internal val impl: MlJsMap) : Style {

  override fun addImage(id: String, image: ImageBitmap, sdf: Boolean) {}

  override fun removeImage(id: String) {}

  override fun getSource(id: String): Source? {
    return null
  }

  override fun getSources(): List<Source> {
    return emptyList()
  }

  override fun addSource(source: Source) {}

  override fun removeSource(source: Source) {}

  override fun getLayer(id: String): Layer? {
    return null
  }

  override fun getLayers(): List<Layer> {
    return emptyList()
  }

  override fun addLayer(layer: Layer) {}

  override fun addLayerAbove(id: String, layer: Layer) {}

  override fun addLayerBelow(id: String, layer: Layer) {}

  override fun addLayerAt(index: Int, layer: Layer) {}

  override fun removeLayer(layer: Layer) {}
}
