package dev.sargunv.maplibrecompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import dev.sargunv.maplibrecompose.core.source.GeoJsonOptions
import dev.sargunv.maplibrecompose.core.source.GeoJsonSource
import dev.sargunv.maplibrecompose.core.source.Source
import io.github.dellisd.spatialk.geojson.GeoJson

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun rememberGeoJsonSource(
  id: String,
  dataUrl: String,
  options: GeoJsonOptions = GeoJsonOptions(),
): Source =
  key(id, options) {
    rememberUserSource(
      factory = { GeoJsonSource(id = id, dataUrl = dataUrl, options = options) },
      update = { setDataUrl(dataUrl) },
    )
  }

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun rememberGeoJsonSource(
  id: String,
  data: GeoJson,
  options: GeoJsonOptions = GeoJsonOptions(),
): Source =
  key(id, options) {
    rememberUserSource(
      factory = { GeoJsonSource(id = id, data = data, options = options) },
      update = { setData(data) },
    )
  }
