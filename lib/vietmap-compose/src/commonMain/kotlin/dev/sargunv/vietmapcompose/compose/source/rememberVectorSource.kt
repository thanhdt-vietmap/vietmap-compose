package dev.sargunv.vietmapcompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import dev.sargunv.vietmapcompose.core.source.VectorSource

/**
 * Remember a new [VectorSource] with the given [id] from the given [uri].
 *
 * @throws IllegalArgumentException if a layer with the given [id] already exists.
 */
@Composable
public fun rememberVectorSource(id: String, uri: String): VectorSource =
  composeKey(id, uri) {
    rememberUserSource(factory = { VectorSource(id = id, uri = uri) }, update = {})
  }
