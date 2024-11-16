package dev.sargunv.maplibrekmp.core.source

import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionBuffer
import cocoapods.MapLibre.MLNShapeSourceOptionClusterProperties
import cocoapods.MapLibre.MLNShapeSourceOptionClusterRadius
import cocoapods.MapLibre.MLNShapeSourceOptionClustered
import cocoapods.MapLibre.MLNShapeSourceOptionLineDistanceMetrics
import cocoapods.MapLibre.MLNShapeSourceOptionMaximumZoomLevel
import cocoapods.MapLibre.MLNShapeSourceOptionMaximumZoomLevelForClustering
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
import dev.sargunv.maplibrekmp.core.layer.ExpressionAdapter.toNSExpression
import dev.sargunv.maplibrekmp.core.toMLNShape
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import io.github.dellisd.spatialk.geojson.GeoJson
import platform.Foundation.NSNumber
import platform.Foundation.NSURL

@PublishedApi
internal actual class GeoJsonSource
actual constructor(id: String, shape: Shape, options: GeoJsonOptions) : UserSource() {
  override val impl: MLNShapeSource

  init {
    val optionMap =
      buildMap<Any?, Any?> {
        put(MLNShapeSourceOptionMaximumZoomLevel, NSNumber(options.maxZoom))
        put(MLNShapeSourceOptionBuffer, NSNumber(options.buffer))
        put(MLNShapeSourceOptionLineDistanceMetrics, NSNumber(options.lineMetrics))
        put(MLNShapeSourceOptionSimplificationTolerance, NSNumber(options.tolerance.toDouble()))
        put(MLNShapeSourceOptionClustered, NSNumber(options.cluster))
        put(MLNShapeSourceOptionMaximumZoomLevelForClustering, NSNumber(options.clusterMaxZoom))
        put(MLNShapeSourceOptionClusterRadius, NSNumber(options.clusterRadius))
        put(
          MLNShapeSourceOptionClusterProperties,
          options.clusterProperties.mapValues { (_, p) ->
            Expression.ofList(listOf(const(p.operator), p.mapper)).toNSExpression()
          },
        )
      }
    impl =
      when (shape) {
        is Shape.Url ->
          MLNShapeSource(identifier = id, URL = NSURL(string = shape.url), options = optionMap)
        is Shape.GeoJson ->
          MLNShapeSource(identifier = id, shape = shape.geoJson.toMLNShape(), options = optionMap)
      }
  }

  actual fun setShapeUrl(url: String) {
    impl.setURL(NSURL(string = url))
  }

  actual fun setShape(geoJson: GeoJson) {
    impl.setShape(geoJson.toMLNShape())
  }
}
