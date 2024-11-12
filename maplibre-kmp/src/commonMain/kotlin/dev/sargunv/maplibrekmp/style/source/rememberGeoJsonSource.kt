package dev.sargunv.maplibrekmp.style.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import dev.sargunv.maplibrekmp.internal.wrapper.source.GeoJsonOptions
import dev.sargunv.maplibrekmp.internal.wrapper.source.GeoJsonSource
import dev.sargunv.maplibrekmp.internal.wrapper.source.Shape

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
