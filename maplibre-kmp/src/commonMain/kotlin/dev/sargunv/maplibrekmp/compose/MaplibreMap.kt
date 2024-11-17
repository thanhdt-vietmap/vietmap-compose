package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import dev.sargunv.maplibrekmp.compose.engine.LayerNode
import dev.sargunv.maplibrekmp.compose.engine.MapNodeApplier
import dev.sargunv.maplibrekmp.compose.engine.StyleNode
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.StyleManager
import dev.sargunv.maplibrekmp.core.UiSettings
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.ExpressionScope
import kotlinx.coroutines.awaitCancellation

@Composable
internal fun rememberStyleCompositionState(
  maybeStyle: Style?,
  content: @Composable ExpressionScope.() -> Unit,
): State<StyleNode?> {
  val ret = remember { mutableStateOf<StyleNode?>(null) }
  val compositionContext = rememberCompositionContext()

  LaunchedEffect(maybeStyle, compositionContext) {
    val rootNode = StyleNode(maybeStyle ?: return@LaunchedEffect).also { ret.value = it }
    val composition = Composition(MapNodeApplier(rootNode), compositionContext)

    composition.setContent {
      CompositionLocalProvider(LocalStyleManager provides rootNode.styleManager) {
        content(Expression.Companion)
      }
    }

    try {
      awaitCancellation()
    } finally {
      println("cancelled!")
      ret.value = null
      rootNode.styleManager.style = Style.Null
      composition.dispose()
    }
  }

  return ret
}

internal val LocalStyleManager =
  staticCompositionLocalOf<StyleManager> { throw IllegalStateException() }

@Composable
public fun MaplibreMap(
  modifier: Modifier = Modifier,
  styleUrl: String = "https://demotiles.maplibre.org/style.json",
  uiSettings: UiSettings = uiSettings(),
  cameraState: CameraState = rememberCameraState(),
  isDebugEnabled: Boolean = false,
  content: @Composable ExpressionScope.() -> Unit = {},
) {
  var rememberedStyle by remember { mutableStateOf<Style?>(null) }
  val styleCompositionState by rememberStyleCompositionState(rememberedStyle, content)

  ComposableMapView(
    modifier = modifier,
    styleUrl = styleUrl,
    update = { map ->
      cameraState.map = map
      map.isDebugEnabled = isDebugEnabled
      map.uiSettings = uiSettings
    },
    onReset = {
      cameraState.map = null
      rememberedStyle = null
    },
    onStyleChanged = { _, style -> rememberedStyle = style },
    onCameraMove = { map -> cameraState.positionState.value = map.cameraPosition },
    onClick = { map, _, xy ->
      styleCompositionState
        ?.children
        ?.mapNotNull { node -> (node as? LayerNode<*>)?.onClick?.let { node.layer.id to it } }
        ?.forEach { (layerId, handle) ->
          val features = map.queryRenderedFeatures(xy, setOf(layerId))
          if (features.isNotEmpty()) handle(features)
        }
    },
    onLongClick = { map, _, xy ->
      styleCompositionState
        ?.children
        ?.mapNotNull { node -> (node as? LayerNode<*>)?.onLongClick?.let { node.layer.id to it } }
        ?.forEach { (layerId, handle) ->
          val features = map.queryRenderedFeatures(xy, setOf(layerId))
          if (features.isNotEmpty()) handle(features)
        }
    },
  )
}
