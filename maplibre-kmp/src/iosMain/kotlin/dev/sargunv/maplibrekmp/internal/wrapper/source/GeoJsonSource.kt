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
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Expressions.const
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding

@PublishedApi
internal actual class GeoJsonSource
actual constructor(override val id: String, shape: Shape, options: GeoJsonOptions) : Source() {
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
          MLNShapeSource(identifier = id, shape = jsonToShape(shape.json), options = optionMap)
      }
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
