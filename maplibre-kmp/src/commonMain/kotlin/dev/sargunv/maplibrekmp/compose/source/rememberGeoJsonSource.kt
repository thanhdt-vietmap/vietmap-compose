package dev.sargunv.maplibrekmp.compose.source

import androidx.compose.runtime.Composable
import dev.sargunv.maplibrekmp.core.data.GeoJsonOptions
import dev.sargunv.maplibrekmp.core.data.ShapeOptions
import dev.sargunv.maplibrekmp.core.source.GeoJsonSource
import dev.sargunv.maplibrekmp.core.source.Source
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun rememberGeoJsonSource(
  id: String,
  shape: ShapeOptions,
  options: GeoJsonOptions = GeoJsonOptions(),
): Source =
  composeKey(id) {
    rememberUserSource(
      factory = { GeoJsonSource(id = id, shape = shape, options = options) },
      update = {
        when (shape) {
          is ShapeOptions.Url -> setShapeUrl(shape.url)
          is ShapeOptions.GeoJson -> setShape(shape.geoJson)
        }
      },
    )
  }
