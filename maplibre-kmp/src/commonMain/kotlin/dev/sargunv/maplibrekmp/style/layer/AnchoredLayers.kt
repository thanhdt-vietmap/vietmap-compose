package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import dev.sargunv.maplibrekmp.internal.compose.AnchoredLayersNode
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.style.AnchoredLayersScope
import dev.sargunv.maplibrekmp.style.LayerContainerScope
import dev.sargunv.maplibrekmp.style.SourceScope

@Composable
public fun SourceScope.AnchoredLayers(
  anchor: LayerAnchor,
  content: @Composable LayerContainerScope.() -> Unit = {},
) {
  // TODO anchor should be passed down as a LocaLProvider instead
  ComposeNode<AnchoredLayersNode, MapNodeApplier>(
    factory = { AnchoredLayersNode(anchor) },
    update = {},
  ) {
    AnchoredLayersScope.content()
  }
}
