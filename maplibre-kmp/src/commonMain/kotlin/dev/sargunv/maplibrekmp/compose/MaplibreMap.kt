package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrekmp.compose.engine.LayerNode
import dev.sargunv.maplibrekmp.compose.engine.MapNodeApplier
import dev.sargunv.maplibrekmp.compose.engine.StyleNode
import dev.sargunv.maplibrekmp.core.ControlSettings
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.StyleManager
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.ExpressionScope
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.coroutines.awaitCancellation

@Composable
public fun MaplibreMap(
  modifier: Modifier = Modifier,
  styleUrl: String = "https://demotiles.maplibre.org/style.json",
  uiPadding: PaddingValues = PaddingValues(8.dp),
  controlSettings: ControlSettings = ControlSettings(),
  cameraState: CameraState = rememberCameraState(),
  isDebugEnabled: Boolean = false,
  onClick: (latLng: Position, xy: Pair<Float, Float>) -> Unit = { _, _ -> },
  onLongClick: (latLng: Position, xy: Pair<Float, Float>) -> Unit = { _, _ -> },
  styleContent: @Composable ExpressionScope.() -> Unit = {},
) {
  val compositionContext = rememberCompositionContext()

  var map by remember { mutableStateOf<PlatformMap?>(null) }
  var style by remember { mutableStateOf<Style?>(null) }

  var styleCompositionRootNode by mutableStateOf<StyleNode?>(null)
  LaunchedEffect(style) {
    style?.let { style ->
      val rootNode = StyleNode(style).also { styleCompositionRootNode = it }
      val composition = Composition(MapNodeApplier(rootNode), compositionContext)
      composition.setContent {
        CompositionLocalProvider(LocalStyleManager provides rootNode.styleManager) {
          styleContent(Expression.Companion)
        }
      }
      try {
        awaitCancellation()
      } finally {
        composition.dispose()
      }
    }
  }

  SideEffect { cameraState.map = map }

  PlatformMapView(
    modifier = modifier,
    styleUrl = styleUrl,
    uiPadding = uiPadding,
    updateMap = {
      it.isDebugEnabled = isDebugEnabled
      it.controlSettings = controlSettings
    },
    onMapLoaded = { map = it },
    onStyleLoaded = { style = it },
    onCameraMove = { cameraState.positionState.value = map!!.cameraPosition },
    onClick = { latLng, xy ->
      onClick(latLng, xy)
      styleCompositionRootNode!!
        .children
        .mapNotNull { node -> (node as? LayerNode<*>)?.onClick?.let { node.layer.id to it } }
        .forEach { (layerId, handle) ->
          val features = map!!.queryRenderedFeatures(xy, setOf(layerId))
          if (features.isNotEmpty()) handle(features)
        }
    },
    onLongClick = { latLng, xy ->
      onLongClick(latLng, xy)
      styleCompositionRootNode!!
        .children
        .mapNotNull { node -> (node as? LayerNode<*>)?.onLongClick?.let { node.layer.id to it } }
        .forEach { (layerId, handle) ->
          val features = map!!.queryRenderedFeatures(xy, setOf(layerId))
          if (features.isNotEmpty()) handle(features)
        }
    },
    onRelease = {
      style = null
      map = null
    },
  )
}

internal val LocalStyleManager =
  staticCompositionLocalOf<StyleManager> { throw IllegalStateException() }
