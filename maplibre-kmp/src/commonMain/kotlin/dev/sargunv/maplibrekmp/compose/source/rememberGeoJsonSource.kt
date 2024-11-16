package dev.sargunv.maplibrekmp.compose.source

import androidx.compose.runtime.Composable
import dev.sargunv.maplibrekmp.core.source.GeoJsonOptions
import dev.sargunv.maplibrekmp.core.source.GeoJsonSource
import dev.sargunv.maplibrekmp.core.source.Shape
import dev.sargunv.maplibrekmp.core.source.Source
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun rememberGeoJsonSource(
  id: String,
  shape: Shape,
  options: GeoJsonOptions = GeoJsonOptions(),
): Source =
  composeKey(id) {
    rememberSource(
      factory = { GeoJsonSource(id = id, shape = shape, options = options) },
      update = {
        when (shape) {
          is Shape.Url -> setShapeUrl(shape.url)
          is Shape.GeoJson -> setShape(shape.geoJson)
        }
      },
    )
  }
