package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Updater
import dev.sargunv.maplibrekmp.compose.engine.LayerNode
import dev.sargunv.maplibrekmp.compose.engine.MapNodeApplier
import dev.sargunv.maplibrekmp.core.layer.UserLayer
import io.github.dellisd.spatialk.geojson.Feature
import dev.sargunv.maplibrekmp.core.layer.Anchor as CoreAnchor

@PublishedApi
@Composable
internal fun <T : UserLayer> LayerNode(
  factory: (anchor: CoreAnchor) -> T,
  update: Updater<LayerNode<T>>.() -> Unit,
  onClick: ((features: List<Feature>) -> Unit)?,
  onLongClick: ((features: List<Feature>) -> Unit)?,
) {
  val anchor = LocalAnchor.current
  ComposeNode<LayerNode<T>, MapNodeApplier>(
    factory = { LayerNode(layer = factory(anchor)) },
    update = {
      update()
      set(onClick) { this.onClick = it }
      set(onLongClick) { this.onLongClick = it }
    },
  )
}
