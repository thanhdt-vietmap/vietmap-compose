package dev.sargunv.vietmapcompose.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Updater
import androidx.compose.runtime.key
import dev.sargunv.vietmapcompose.compose.FeaturesClickHandler
import dev.sargunv.vietmapcompose.compose.MaplibreComposable
import dev.sargunv.vietmapcompose.compose.engine.LayerNode
import dev.sargunv.vietmapcompose.compose.engine.MapNodeApplier
import dev.sargunv.vietmapcompose.core.layer.Layer

@Composable
@MaplibreComposable
internal fun <T : Layer> LayerNode(
  factory: () -> T,
  update: Updater<LayerNode<T>>.() -> Unit,
  onClick: FeaturesClickHandler?,
  onLongClick: FeaturesClickHandler?,
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
