package dev.sargunv.maplibrekmp.style.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.remember
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.internal.compose.SourceNode
import dev.sargunv.maplibrekmp.internal.wrapper.source.GeoJsonSource
import dev.sargunv.maplibrekmp.style.IncrementingId
import dev.sargunv.maplibrekmp.style.SourceHandle
import dev.sargunv.maplibrekmp.style.SourceScope
import dev.sargunv.maplibrekmp.style.StyleScope

@Composable
public fun StyleScope.GeoJsonSource(
  url: String,
  tolerance: Float? = null,
  content: @Composable SourceScope.(source: SourceHandle) -> Unit,
) {
  val id = remember { IncrementingId.next() }
  ComposeNode<SourceNode<GeoJsonSource>, MapNodeApplier>(
    factory = { SourceNode(GeoJsonSource(id = id, url = url, tolerance = tolerance)) },
    update = {},
  ) {
    SourceScope.content(SourceHandle(id))
  }
}
