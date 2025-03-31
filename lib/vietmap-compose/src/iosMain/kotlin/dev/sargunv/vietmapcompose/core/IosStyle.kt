package dev.sargunv.vietmapcompose.core

import androidx.compose.ui.graphics.ImageBitmap
import cocoapods.VietMap.MLNRasterTileSource
import cocoapods.VietMap.MLNShapeSource
import cocoapods.VietMap.MLNSource
import cocoapods.VietMap.MLNStyle
import cocoapods.VietMap.MLNStyleLayer
import cocoapods.VietMap.MLNVectorTileSource
import dev.sargunv.VietMapcompose.core.util.toUIImage
import dev.sargunv.vietmapcompose.core.layer.Layer
import dev.sargunv.vietmapcompose.core.layer.UnknownLayer
import dev.sargunv.vietmapcompose.core.source.GeoJsonSource
import dev.sargunv.vietmapcompose.core.source.RasterSource
import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.core.source.UnknownSource
import dev.sargunv.vietmapcompose.core.source.VectorSource

internal class IosStyle(style: MLNStyle, private val getScale: () -> Float) : Style {
  private var impl: MLNStyle = style

  override fun addImage(id: String, image: ImageBitmap, sdf: Boolean) {
    impl.setImage(image.toUIImage(getScale(), sdf), forName = id)
  }

  override fun removeImage(id: String) {
    impl.removeImageForName(id)
  }

  private fun MLNSource.toSource() =
    when (this) {
      is MLNVectorTileSource -> VectorSource(this)
      is MLNShapeSource -> GeoJsonSource(this)
      is MLNRasterTileSource -> RasterSource(this)
      else -> UnknownSource(this)
    }

  override fun getSource(id: String): Source? {
    return impl.sourceWithIdentifier(id)?.toSource()
  }

  override fun getSources(): List<Source> {
    return impl.sources.map { (it as MLNSource).toSource() }
  }

  override fun addSource(source: Source) {
    impl.addSource(source.impl)
  }

  override fun removeSource(source: Source) {
    impl.removeSource(source.impl)
  }

  override fun getLayer(id: String): Layer? {
    return impl.layerWithIdentifier(id)?.let { UnknownLayer(it) }
  }

  override fun getLayers(): List<Layer> {
    return impl.layers.map { UnknownLayer(it as MLNStyleLayer) }
  }

  override fun addLayer(layer: Layer) {
    impl.addLayer(layer.impl)
  }

  override fun addLayerAbove(id: String, layer: Layer) {
    impl.insertLayer(layer.impl, aboveLayer = impl.layerWithIdentifier(id)!!)
  }

  override fun addLayerBelow(id: String, layer: Layer) {
    impl.insertLayer(layer.impl, belowLayer = impl.layerWithIdentifier(id)!!)
  }

  override fun addLayerAt(index: Int, layer: Layer) {
    impl.insertLayer(layer.impl, atIndex = index.toULong())
  }

  override fun removeLayer(layer: Layer) {
    impl.removeLayer(layer.impl)
  }
}
