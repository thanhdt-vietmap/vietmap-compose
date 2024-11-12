package dev.sargunv.maplibrekmp.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import dev.sargunv.maplibrekmp.core.source.GeoJsonOptions
import dev.sargunv.maplibrekmp.core.source.GeoJsonSource
import dev.sargunv.maplibrekmp.core.source.Shape

@Composable
public inline fun rememberGeoJsonSource(
  key: String,
  shape: Shape,
  options: GeoJsonOptions = GeoJsonOptions(),
): SourceHandle =
  composeKey(key) {
    rememberSource(
      key = key,
      factory = { id -> GeoJsonSource(id = id, shape = shape, options = options) },
      update = {
        when (shape) {
          is Shape.Url -> setDataUrl(shape.url)
          is Shape.GeoJson -> setDataJson(shape.json)
        }
      },
    )
  }
