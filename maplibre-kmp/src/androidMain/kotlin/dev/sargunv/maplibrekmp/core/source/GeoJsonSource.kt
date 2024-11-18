package dev.sargunv.maplibrekmp.core.source

import dev.sargunv.maplibrekmp.core.util.correctedAndroidUri
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import io.github.dellisd.spatialk.geojson.GeoJson
import org.maplibre.android.style.sources.GeoJsonOptions as MLNGeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource as MLNGeoJsonSource

@PublishedApi
internal actual class GeoJsonSource
actual constructor(id: String, shape: Shape, options: GeoJsonOptions) : Source() {

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
          withClusterProperty(
            key,
            const(value.operator).toMLNExpression()!!,
            value.mapper.toMLNExpression()!!
          )
        }
      }
    impl =
      when (shape) {
        is Shape.Url ->
          MLNGeoJsonSource(id = id, uri = shape.url.correctedAndroidUri(), options = optionMap)
        is Shape.GeoJson ->
          MLNGeoJsonSource(id = id, geoJson = shape.geoJson.json(), options = optionMap)
      }
  }

  actual fun setShapeUrl(url: String) {
    impl.setUri(url.correctedAndroidUri())
  }

  actual fun setShape(geoJson: GeoJson) {
    impl.setGeoJson(geoJson.json())
  }
}
