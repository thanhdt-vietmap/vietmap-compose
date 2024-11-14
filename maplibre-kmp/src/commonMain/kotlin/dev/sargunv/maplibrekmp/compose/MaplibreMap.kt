package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import dev.sargunv.maplibrekmp.compose.engine.MapNodeApplier
import dev.sargunv.maplibrekmp.compose.engine.StyleNode
import dev.sargunv.maplibrekmp.core.CameraPadding
import dev.sargunv.maplibrekmp.core.LatLng
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.StyleManager
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.ExpressionScope
import kotlinx.coroutines.awaitCancellation

@Stable
public class CameraState {
  internal var map: PlatformMap? = null

  public var bearing: Double by mutableStateOf(0.0)
  public var padding: CameraPadding by mutableStateOf(CameraPadding(0.0, 0.0, 0.0, 0.0))
  public var target: LatLng by mutableStateOf(LatLng(0.0, 0.0))
  public var tilt: Double by mutableStateOf(0.0)
  public var zoom: Double by mutableStateOf(0.0)

  internal fun updateFromMap() {
    map!!.let {
      bearing = it.cameraBearing
      padding = it.cameraPadding
      target = it.cameraTarget
      tilt = it.cameraTilt
      zoom = it.cameraZoom
    }
  }

  internal fun applyToMap() {
    map!!.let {
      it.cameraBearing = bearing
      it.cameraPadding = padding
      it.cameraTarget = target
      it.cameraTilt = tilt
      it.cameraZoom = zoom
    }
  }
}

@Composable public fun rememberCameraState(): CameraState = remember { CameraState() }

@Composable
public fun MaplibreMap(
  modifier: Modifier = Modifier,
  styleUrl: String = "https://demotiles.maplibre.org/style.json",
  uiSettings: MapUiSettings = MapUiSettings(),
  cameraState: CameraState = rememberCameraState(),
  styleContent: @Composable ExpressionScope.() -> Unit = {},
) {
  var style by remember { mutableStateOf<Style?>(null) }
  val compositionContext = rememberCompositionContext()

  PlatformMapView(
    modifier = modifier,
    uiPadding = uiSettings.uiPadding,
    styleUrl = styleUrl,
    updateMap = { map ->
      println("updated map, zoom=${cameraState.zoom}")
      map.isDebugEnabled = uiSettings.isDebugEnabled
      map.isLogoEnabled = uiSettings.isLogoEnabled
      map.isAttributionEnabled = uiSettings.isAttributionEnabled
      map.isCompassEnabled = uiSettings.isCompassEnabled
      map.isTiltGesturesEnabled = uiSettings.isTiltGesturesEnabled
      map.isZoomGesturesEnabled = uiSettings.isZoomGesturesEnabled
      map.isRotateGesturesEnabled = uiSettings.isRotateGesturesEnabled
      map.isScrollGesturesEnabled = uiSettings.isScrollGesturesEnabled
      cameraState.applyToMap()
    },
    onMapLoaded = {
      cameraState.map = it
      cameraState.applyToMap()
    },
    onStyleLoaded = { style = it },
    onCameraMove = { cameraState.updateFromMap() },
    onClick = { println("onClick: $it") },
    onLongClick = { println("onLongClick: $it") },
    onRelease = { style = null },
  )

  LaunchedEffect(style) {
    style?.let { style ->
      val rootNode = StyleNode(style)
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
}

internal val LocalStyleManager =
  staticCompositionLocalOf<StyleManager> { throw IllegalStateException() }
