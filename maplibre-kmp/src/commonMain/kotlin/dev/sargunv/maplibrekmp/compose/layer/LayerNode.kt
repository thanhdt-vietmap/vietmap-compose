package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Updater
import dev.sargunv.maplibrekmp.compose.engine.LayerNode
import dev.sargunv.maplibrekmp.compose.engine.MapNodeApplier
import dev.sargunv.maplibrekmp.core.layer.UserLayer
import dev.sargunv.maplibrekmp.core.layer.Anchor as CoreAnchor

@PublishedApi
@Composable
internal fun <T : UserLayer> LayerNode(
  key: String,
  factory: (id: String, anchor: CoreAnchor) -> T,
  update: Updater<LayerNode<T>>.() -> Unit,
) {
  val anchor = LocalAnchor.current
  ComposeNode<LayerNode<T>, MapNodeApplier>(
    factory = { LayerNode(layer = factory(key, anchor)) },
    update = update,
  )
}
