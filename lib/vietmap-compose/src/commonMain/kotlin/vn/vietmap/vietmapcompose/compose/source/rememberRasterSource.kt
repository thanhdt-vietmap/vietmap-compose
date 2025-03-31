package vn.vietmap.vietmapcompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import vn.vietmap.vietmapcompose.core.source.DEFAULT_RASTER_TILE_SIZE
import vn.vietmap.vietmapcompose.core.source.RasterSource

/**
 * Remember a new [RasterSource] with the given [id] and [tileSize] from the given [uri].
 *
 * @throws IllegalArgumentException if a layer with the given [id] already exists.
 */
@Composable
public fun rememberRasterSource(
  id: String,
  uri: String,
  tileSize: Int = DEFAULT_RASTER_TILE_SIZE,
): RasterSource =
  composeKey(id, uri, tileSize) {
    rememberUserSource(
      factory = { RasterSource(id = id, uri = uri, tileSize = tileSize) },
      update = {},
    )
  }
