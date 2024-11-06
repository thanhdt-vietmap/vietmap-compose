package dev.sargunv.maplibrekmp.map

import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
import dev.sargunv.maplibrekmp.style.Source
import platform.Foundation.NSNumber
import platform.Foundation.NSURL

internal fun Source.GeoJson.toNativeSource(id: String): MLNShapeSource {
  return MLNShapeSource(
    identifier = id,
    URL = NSURL(string = url),
    options =
      buildMap {
        tolerance?.let { put(MLNShapeSourceOptionSimplificationTolerance, NSNumber(it.toDouble())) }
      },
  )
}

internal fun Source.toNativeSource(): MLNShapeSource {
  return when (this) {
    is Source.GeoJson -> toNativeSource(id)
  }
}
