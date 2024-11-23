package dev.sargunv.maplibrecompose.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Updater
import androidx.compose.runtime.key
import dev.sargunv.maplibrecompose.compose.engine.LayerNode
import dev.sargunv.maplibrecompose.compose.engine.MapNodeApplier
import dev.sargunv.maplibrecompose.core.layer.Layer
import io.github.dellisd.spatialk.geojson.Feature

@PublishedApi
@Composable
internal fun <T : Layer> LayerNode(
  factory: () -> T,
  update: Updater<LayerNode<T>>.() -> Unit,
  onClick: ((features: List<Feature>) -> Unit)?,
  onLongClick: ((features: List<Feature>) -> Unit)?,
) {
  val anchor = LocalAnchor.current
  key(factory, anchor) {
    ComposeNode<LayerNode<T>, MapNodeApplier>(
      factory = { LayerNode(layer = factory(), anchor = anchor) },
      update = {
        update()
        set(onClick) { this.onClick = it }
        set(onLongClick) { this.onLongClick = it }
      },
    )
  }
}
