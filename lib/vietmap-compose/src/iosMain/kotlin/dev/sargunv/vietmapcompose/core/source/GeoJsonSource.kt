package dev.sargunv.vietmapcompose.core.source

import cocoapods.VietMap.MLNShapeSource
import cocoapods.VietMap.MLNShapeSourceOptionBuffer
import cocoapods.VietMap.MLNShapeSourceOptionClusterProperties
import cocoapods.VietMap.MLNShapeSourceOptionClusterRadius
import cocoapods.VietMap.MLNShapeSourceOptionClustered
import cocoapods.VietMap.MLNShapeSourceOptionLineDistanceMetrics
import cocoapods.VietMap.MLNShapeSourceOptionMaximumZoomLevel
import cocoapods.VietMap.MLNShapeSourceOptionMaximumZoomLevelForClustering
import cocoapods.VietMap.MLNShapeSourceOptionMinimumZoomLevel
import cocoapods.VietMap.MLNShapeSourceOptionSimplificationTolerance
import dev.sargunv.VietMapcompose.core.util.toMLNShape
import dev.sargunv.VietMapcompose.core.util.toNSExpression
import dev.sargunv.vietmapcompose.expressions.ExpressionContext
import dev.sargunv.vietmapcompose.expressions.ast.FunctionCall
import io.github.dellisd.spatialk.geojson.GeoJson
import platform.Foundation.NSNumber
import platform.Foundation.NSURL

public actual class GeoJsonSource : Source {
  override val impl: MLNShapeSource

  internal constructor(source: MLNShapeSource) {
    impl = source
  }

  public actual constructor(id: String, uri: String, options: GeoJsonOptions) {
    impl =
      MLNShapeSource(identifier = id, URL = NSURL(string = uri), options = buildOptionMap(options))
  }

  public actual constructor(id: String, data: GeoJson, options: GeoJsonOptions) {
    impl =
      MLNShapeSource(identifier = id, shape = data.toMLNShape(), options = buildOptionMap(options))
  }

  private fun buildOptionMap(options: GeoJsonOptions) =
    buildMap<Any?, Any?> {
      put(MLNShapeSourceOptionMinimumZoomLevel, NSNumber(options.minZoom))
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
          FunctionCall.of(p.operator, p.mapper).compile(ExpressionContext.None).toNSExpression()
        },
      )
    }

  public actual fun setUri(uri: String) {
    impl.setURL(NSURL(string = uri))
  }

  public actual fun setData(geoJson: GeoJson) {
    impl.setShape(geoJson.toMLNShape())
  }
}
