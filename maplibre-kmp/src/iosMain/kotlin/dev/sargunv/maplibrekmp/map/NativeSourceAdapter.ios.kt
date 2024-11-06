package dev.sargunv.maplibrekmp.map

import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
import cocoapods.MapLibre.MLNSource
import dev.sargunv.maplibrekmp.style.Source
import platform.Foundation.NSNumber
import platform.Foundation.NSURL

internal object NativeSourceAdapter : StyleManager.Adapter<Source, MLNSource> {
  private fun Source.GeoJson.toNativeSource(id: String): MLNShapeSource {
    return MLNShapeSource(
      identifier = id,
      URL = NSURL(string = url),
      options =
        buildMap {
          tolerance?.let {
            put(MLNShapeSourceOptionSimplificationTolerance, NSNumber(it.toDouble()))
          }
        },
    )
  }

  override fun convert(common: Source): MLNSource {
    return when (common) {
      is Source.GeoJson -> common.toNativeSource(common.id)
    }
  }
}
