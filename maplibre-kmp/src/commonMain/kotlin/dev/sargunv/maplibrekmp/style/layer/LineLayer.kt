package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.internal.compose.LayerNode
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.internal.wrapper.layer.LineLayer
import dev.sargunv.maplibrekmp.style.IncrementingId
import dev.sargunv.maplibrekmp.style.LayerContainerScope
import dev.sargunv.maplibrekmp.style.SourceHandle
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.getSource

@Composable
public fun LayerContainerScope.LineLayer(
  sourceHandle: SourceHandle,
  lineColor: Expression<Color>,
  lineWidth: Expression<Number>,
) {
  val id = remember { IncrementingId.next() }
  val source = getSource(sourceHandle) ?: return
  ComposeNode<LayerNode<LineLayer>, MapNodeApplier>(
    factory = { LayerNode(LineLayer(id = id, source = source)) },
    update = {
      set(lineColor) { layer.setColor(it) }
      set(lineWidth) { layer.setWidth(it) }
    },
  )
}
