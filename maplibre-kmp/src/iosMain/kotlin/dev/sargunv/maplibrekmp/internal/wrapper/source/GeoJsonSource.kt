package dev.sargunv.maplibrekmp.internal.wrapper.source

import cocoapods.MapLibre.MLNShape
import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionBuffer
import cocoapods.MapLibre.MLNShapeSourceOptionClusterProperties
import cocoapods.MapLibre.MLNShapeSourceOptionClusterRadius
import cocoapods.MapLibre.MLNShapeSourceOptionClustered
import cocoapods.MapLibre.MLNShapeSourceOptionLineDistanceMetrics
import cocoapods.MapLibre.MLNShapeSourceOptionMaximumZoomLevel
import cocoapods.MapLibre.MLNShapeSourceOptionMaximumZoomLevelForClustering
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
import dev.sargunv.maplibrekmp.internal.wrapper.layer.ExpressionAdapter.toNSExpression
import dev.sargunv.maplibrekmp.internal.wrapper.toNSData
import dev.sargunv.maplibrekmp.style.AnchoredLayersScope.const
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.source.GeoJsonOptions
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding

internal actual class GeoJsonSource
actual constructor(
  override val id: String,
  dataUrl: String?,
  dataJson: String?,
  options: GeoJsonOptions,
) : Source() {
  override val impl: MLNShapeSource

  init {
    require((dataUrl != null) or (dataJson != null)) {
      "Either dataUrl or dataJson must be provided"
    }
    require((dataUrl == null) or (dataJson == null)) {
      "Only one of dataUrl or dataJson may be provided"
    }

    val mlnOptions: Map<Any?, *> = buildMap {
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
      if (dataUrl != null)
        MLNShapeSource(identifier = id, URL = NSURL(string = dataUrl), options = mlnOptions)
      else MLNShapeSource(identifier = id, shape = jsonToShape(dataJson!!), options = mlnOptions)
  }

  actual fun setDataUrl(url: String) {
    impl.setURL(NSURL(string = url))
  }

  actual fun setDataJson(json: String) {
    impl.setShape(jsonToShape(json))
  }

  private fun jsonToShape(json: String): MLNShape {
    return MLNShape.shapeWithData(
      data = json.encodeToByteArray().toNSData(),
      encoding = NSUTF8StringEncoding,
      error = null,
    )!!
  }
}
