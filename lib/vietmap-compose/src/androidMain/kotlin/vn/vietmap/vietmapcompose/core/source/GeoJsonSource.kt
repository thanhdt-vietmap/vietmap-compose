package vn.vietmap.vietmapcompose.core.source

import vn.vietmap.vietmapcompose.core.util.correctedAndroidUri
import vn.vietmap.vietmapcompose.core.util.toMLNExpression
import vn.vietmap.vietmapcompose.expressions.ExpressionContext
import vn.vietmap.vietmapcompose.expressions.dsl.const
import io.github.dellisd.spatialk.geojson.GeoJson
import java.net.URI
import vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions as MLNGeoJsonOptions
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource as MLNGeoJsonSource

public actual class GeoJsonSource : Source {
  override val impl: MLNGeoJsonSource

  internal constructor(source: MLNGeoJsonSource) {
    impl = source
  }

  public actual constructor(id: String, uri: String, options: GeoJsonOptions) {
    impl = MLNGeoJsonSource(id, URI(uri.correctedAndroidUri()), buildOptionMap(options))
  }

  public actual constructor(id: String, data: GeoJson, options: GeoJsonOptions) {
    impl = MLNGeoJsonSource(id, data.json(), buildOptionMap(options))
  }

  private fun buildOptionMap(options: GeoJsonOptions) =
    MLNGeoJsonOptions().apply {
      withMinZoom(options.minZoom)
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
          value.mapper.compile(ExpressionContext.None).toMLNExpression()!!,
        )
      }
    }

  public actual fun setUri(uri: String) {
    impl.setUri(uri.correctedAndroidUri())
  }

  public actual fun setData(geoJson: GeoJson) {
    impl.setGeoJson(geoJson.json())
  }
}
