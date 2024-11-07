package dev.sargunv.maplibrekmp.style.source

import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
import platform.Foundation.NSNumber
import platform.Foundation.NSURL

internal actual class GeoJsonSource
actual constructor(override val id: String, url: String, tolerance: Float?) : Source() {
  override val impl =
    MLNShapeSource(
      identifier = id,
      URL = NSURL(string = url),
      options =
        buildMap {
          tolerance?.let {
            put(MLNShapeSourceOptionSimplificationTolerance, NSNumber(it.toDouble()))
          } ?: remove(MLNShapeSourceOptionSimplificationTolerance)
          Unit
        },
    )
}
