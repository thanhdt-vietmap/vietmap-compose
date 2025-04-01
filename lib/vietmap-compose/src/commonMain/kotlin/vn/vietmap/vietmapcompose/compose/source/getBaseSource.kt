package vn.vietmap.vietmapcompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import vn.vietmap.vietmapcompose.compose.engine.LocalStyleNode
import vn.vietmap.vietmapcompose.core.source.Source

/**
 * Get the source with the given [id] from the base style specified via the `styleUri` parameter in
 * [MaplibreMap][vn.vietmap.vietmapcompose.compose.VietMapGLCompose].
 *
 * @throws IllegalStateException if the source does not exist
 */
@Composable
public fun getBaseSource(id: String): Source {
  val node = LocalStyleNode.current
  return remember(node, id) { node.sourceManager.getBaseSource(id) }
}
