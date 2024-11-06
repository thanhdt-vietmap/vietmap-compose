package dev.sargunv.maplibrekmp.map

import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
import dev.sargunv.maplibrekmp.style.source.Source
import platform.Foundation.NSNumber
import platform.Foundation.NSURL

internal fun Source.GeoJson.toNativeSource(id: String) =
  MLNShapeSource(
    identifier = id,
    URL = NSURL(string = url),
    options =
      buildMap {
        tolerance?.let { put(MLNShapeSourceOptionSimplificationTolerance, NSNumber(it.toDouble())) }
      },
  )
