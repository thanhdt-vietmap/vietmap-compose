package vn.vietmap.vietmapcompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import vn.vietmap.vietmapcompose.compose.engine.LocalStyleNode
import vn.vietmap.vietmapcompose.core.source.Source

@Composable
internal fun <T : Source> rememberUserSource(factory: () -> T, update: T.() -> Unit): T {
  val node = LocalStyleNode.current
  val source = remember(node) { factory().also { node.sourceManager.addReference(it) } }
  remember(source, update) { source.update() }
  DisposableEffect(node, source) { onDispose { node.sourceManager.removeReference(source) } }
  return source
}
