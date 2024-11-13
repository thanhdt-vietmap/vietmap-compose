package dev.sargunv.maplibrekmp.core.source

import dev.sargunv.maplibrekmp.core.correctedAndroidUri
import dev.sargunv.maplibrekmp.core.layer.ExpressionAdapter.convert
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import org.maplibre.android.style.sources.GeoJsonOptions as MLNGeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource as MLNGeoJsonSource

@PublishedApi
internal actual class GeoJsonSource
actual constructor(id: String, shape: Shape, options: GeoJsonOptions) : UserSource() {

  override val impl: MLNGeoJsonSource

  init {
    val optionMap =
      MLNGeoJsonOptions().apply {
        withMaxZoom(options.maxZoom)
        withBuffer(options.buffer)
        withTolerance(options.tolerance)
        withLineMetrics(options.lineMetrics)
        withCluster(options.cluster)
        withClusterMaxZoom(options.clusterMaxZoom)
        withClusterRadius(options.clusterRadius)
        options.clusterProperties.forEach { (key, value) ->
          withClusterProperty(key, const(value.operator).convert()!!, value.mapper.convert()!!)
        }
      }
    impl =
      when (shape) {
        is Shape.Url ->
          MLNGeoJsonSource(id = id, uri = shape.url.correctedAndroidUri(), options = optionMap)
        is Shape.GeoJson -> MLNGeoJsonSource(id = id, geoJson = shape.json, options = optionMap)
      }
  }

  actual fun setDataUrl(url: String) {
    impl.setUri(url.correctedAndroidUri())
  }

  actual fun setDataJson(json: String) {
    impl.setGeoJson(json)
  }
}
