package dev.sargunv.maplibrecompose

import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
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
