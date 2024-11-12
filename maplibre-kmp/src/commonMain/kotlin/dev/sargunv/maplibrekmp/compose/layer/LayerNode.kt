package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Updater
import dev.sargunv.maplibrekmp.compose.engine.LayerNode
import dev.sargunv.maplibrekmp.compose.engine.MapNodeApplier
import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.compose.IncrementingId

@PublishedApi
@Composable
internal fun <T : Layer> LayerNode(
  key: String,
  factory: (id: String) -> T,
  update: Updater<LayerNode<T>>.() -> Unit,
) {
  val anchor = LocalAnchor.current
  ComposeNode<LayerNode<T>, MapNodeApplier>(
    factory = { LayerNode(layer = factory(IncrementingId.next(key)), anchor = anchor) },
    update = update,
  )
}
