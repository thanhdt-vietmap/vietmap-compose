package dev.sargunv.vietmapcompose.core

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import dev.sargunv.vietmapcompose.core.layer.Layer
import dev.sargunv.vietmapcompose.core.layer.UnknownLayer
import dev.sargunv.vietmapcompose.core.source.GeoJsonSource
import dev.sargunv.vietmapcompose.core.source.RasterSource
import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.core.source.UnknownSource
import dev.sargunv.vietmapcompose.core.source.VectorSource
import vn.vietmap.vietmapsdk.maps.Style as MLNStyle
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource as MLNGeoJsonSource
import vn.vietmap.vietmapsdk.style.sources.RasterSource as MLNRasterSource
import vn.vietmap.vietmapsdk.style.sources.Source as MLNSource
import vn.vietmap.vietmapsdk.style.sources.VectorSource as MLNVectorSource

internal class AndroidStyle(style: MLNStyle) : Style {
  private var impl: MLNStyle = style

  override fun addImage(id: String, image: ImageBitmap, sdf: Boolean) {
    impl.addImage(id, image.asAndroidBitmap(), sdf)
  }

  override fun removeImage(id: String) {
    impl.removeImage(id)
  }

  private fun MLNSource.toSource() =
    when (this) {
      is MLNVectorSource -> VectorSource(this)
      is MLNGeoJsonSource -> GeoJsonSource(this)
      is MLNRasterSource -> RasterSource(this)
      else -> UnknownSource(this)
    }

  override fun getSource(id: String): Source? {
    return impl.getSource(id)?.toSource()
  }

  override fun getSources(): List<Source> {
    return impl.sources.map { it.toSource() }
  }

  override fun addSource(source: Source) {
    impl.addSource(source.impl)
  }

  override fun removeSource(source: Source) {
//    impl.removeSource(source.impl)
  }

  override fun getLayer(id: String): Layer? {
    return impl.getLayer(id)?.let { UnknownLayer(it) }
  }

  override fun getLayers(): List<Layer> {
    return impl.layers.map { UnknownLayer(it) }
  }

  override fun addLayer(layer: Layer) {
    impl.addLayer(layer.impl)
  }

  override fun addLayerAbove(id: String, layer: Layer) {
    impl.addLayerAbove(layer.impl, id)
  }

  override fun addLayerBelow(id: String, layer: Layer) {
    impl.addLayerBelow(layer.impl, id)
  }

  override fun addLayerAt(index: Int, layer: Layer) {
    impl.addLayerAt(layer.impl, index)
  }

  override fun removeLayer(layer: Layer) {
//    impl.removeLayer(layer.impl)
  }
}
