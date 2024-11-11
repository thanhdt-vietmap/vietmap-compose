package dev.sargunv.maplibrekmp.style.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.internal.compose.SourceNode
import dev.sargunv.maplibrekmp.internal.wrapper.source.ClusterProperty
import dev.sargunv.maplibrekmp.internal.wrapper.source.GeoJsonSource
import dev.sargunv.maplibrekmp.style.IncrementingId
import dev.sargunv.maplibrekmp.style.SourceHandle
import dev.sargunv.maplibrekmp.style.SourceScope
import dev.sargunv.maplibrekmp.style.StyleScope

@Immutable
public data class GeoJsonOptions(
  val maxZoom: Int = 18,
  val buffer: Int = 128,
  val tolerance: Float = 0.375f,
  val cluster: Boolean = false,
  val clusterRadius: Int = 50,
  val clusterMaxZoom: Int = maxZoom - 1,
  val clusterProperties: Map<String, ClusterProperty> = emptyMap(),
  val lineMetrics: Boolean = false,
)

@Composable
public fun StyleScope.GeoJsonUrlSource(
  dataUrl: String,
  options: GeoJsonOptions = GeoJsonOptions(),
  content: @Composable SourceScope.(source: SourceHandle) -> Unit = {},
) {
  val id = remember { IncrementingId.next() }
  key(id, options) {
    ComposeNode<SourceNode<GeoJsonSource>, MapNodeApplier>(
      factory = { SourceNode(GeoJsonSource(id = id, dataUrl = dataUrl, options = options)) },
      update = { update(dataUrl) { source.setDataUrl(it) } },
    ) {
      SourceScope.content(SourceHandle(id))
    }
  }
}

@Composable
public fun StyleScope.GeoJsonSource(
  dataJson: String,
  options: GeoJsonOptions = GeoJsonOptions(),
  content: @Composable SourceScope.(source: SourceHandle) -> Unit = {},
) {
  val id = remember { IncrementingId.next() }
  key(id, options) {
    ComposeNode<SourceNode<GeoJsonSource>, MapNodeApplier>(
      factory = { SourceNode(GeoJsonSource(id = id, dataJson = dataJson, options = options)) },
      update = { update(dataJson) { source.setDataJson(it) } },
    ) {
      SourceScope.content(SourceHandle(id))
    }
  }
}
