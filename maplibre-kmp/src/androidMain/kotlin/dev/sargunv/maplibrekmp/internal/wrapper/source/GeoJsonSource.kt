package dev.sargunv.maplibrekmp.internal.wrapper.source

import dev.sargunv.maplibrekmp.internal.wrapper.correctedAndroidUri
import dev.sargunv.maplibrekmp.internal.wrapper.layer.ExpressionAdapter.convert
import dev.sargunv.maplibrekmp.style.expression.Expressions.const
import dev.sargunv.maplibrekmp.style.source.GeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonOptions as MLNGeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource as MLNGeoJsonSource

internal actual class GeoJsonSource
actual constructor(
  override val id: String,
  dataUrl: String?,
  dataJson: String?,
  options: GeoJsonOptions,
) : Source() {

  override val impl: MLNGeoJsonSource

  init {
    require((dataUrl != null) or (dataJson != null)) {
      "Either dataUrl or dataJson must be provided"
    }
    require((dataUrl == null) or (dataJson == null)) {
      "Only one of dataUrl or dataJson may be provided"
    }

    val mlnOptions =
      MLNGeoJsonOptions().apply {
        withMaxZoom(options.maxZoom)
        withBuffer(options.buffer)
        withTolerance(options.tolerance)
        withLineMetrics(options.lineMetrics)
        withCluster(options.cluster)
        withClusterMaxZoom(options.clusterMaxZoom)
        withClusterRadius(options.clusterRadius)
        options.clusterProperties.forEach { (key, value) ->
          withClusterProperty(key, const(value.operator).convert(), value.mapper.convert())
        }
      }

    impl =
      if (dataUrl != null)
        MLNGeoJsonSource(id = id, uri = dataUrl.correctedAndroidUri(), options = mlnOptions)
      else MLNGeoJsonSource(id = id, geoJson = dataJson!!, options = mlnOptions)
  }

  actual fun setDataUrl(url: String) {
    impl.setUri(url.correctedAndroidUri())
  }

  actual fun setDataJson(json: String) {
    impl.setGeoJson(json)
  }
}
