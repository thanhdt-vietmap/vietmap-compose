package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Updater
import dev.sargunv.maplibrekmp.internal.compose.LayerNode
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.internal.wrapper.layer.Layer
import dev.sargunv.maplibrekmp.style.IncrementingId

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
