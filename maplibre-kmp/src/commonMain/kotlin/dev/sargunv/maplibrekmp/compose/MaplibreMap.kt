package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
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

@Immutable
public data class CameraPosition(
  public val bearing: Double = 0.0,
  public val padding: CameraPadding = CameraPadding(0.0, 0.0, 0.0, 0.0),
  public val target: LatLng = LatLng(0.0, 0.0),
  public val tilt: Double = 0.0,
  public val zoom: Double = 1.0,
)

@Stable
public class CameraState internal constructor(firstPosition: CameraPosition) {
  internal var map: PlatformMap? = null
  public var position: CameraPosition by mutableStateOf(firstPosition)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is CameraState) return false
    return position == other.position
  }

  override fun hashCode(): Int {
    return position.hashCode()
  }
}

@Composable
public fun rememberCameraState(firstPosition: CameraPosition = CameraPosition()): CameraState =
  remember {
    CameraState(firstPosition)
  }

@Composable
public fun MaplibreMap(
  modifier: Modifier = Modifier,
  styleUrl: String = "https://demotiles.maplibre.org/style.json",
  uiSettings: MapUiSettings = MapUiSettings(),
  cameraState: CameraState = rememberCameraState(),
  styleContent: @Composable ExpressionScope.() -> Unit = {},
) {
  val compositionContext = rememberCompositionContext()

  var map by remember { mutableStateOf<PlatformMap?>(null) }
  var style by remember { mutableStateOf<Style?>(null) }
  remember(map, cameraState) { cameraState.map = map }

  var lastUiSettings by remember { mutableStateOf<MapUiSettings?>(null) }
  var lastPosition by remember { mutableStateOf<CameraPosition?>(null) }
  val position = cameraState.position

  PlatformMapView(
    modifier = modifier,
    uiPadding = uiSettings.uiPadding,
    styleUrl = styleUrl,
    updateMap = {
      if (lastUiSettings != uiSettings) {
        it.isDebugEnabled = uiSettings.isDebugEnabled
        it.isLogoEnabled = uiSettings.isLogoEnabled
        it.isAttributionEnabled = uiSettings.isAttributionEnabled
        it.isCompassEnabled = uiSettings.isCompassEnabled
        it.isTiltGesturesEnabled = uiSettings.isTiltGesturesEnabled
        it.isZoomGesturesEnabled = uiSettings.isZoomGesturesEnabled
        it.isRotateGesturesEnabled = uiSettings.isRotateGesturesEnabled
        it.isScrollGesturesEnabled = uiSettings.isScrollGesturesEnabled
        lastUiSettings = uiSettings
      }
      if (position != lastPosition) {
        it.cameraBearing = position.bearing
        it.cameraPadding = position.padding
        it.cameraTarget = position.target
        it.cameraTilt = position.tilt
        it.cameraZoom = position.zoom
        lastPosition = position
      }
    },
    onMapLoaded = { map = it },
    onStyleLoaded = { style = it },
    onCameraMove = {
      map!!.let {
        val pos =
          CameraPosition(
            bearing = it.cameraBearing,
            padding = it.cameraPadding,
            target = it.cameraTarget,
            tilt = it.cameraTilt,
            zoom = it.cameraZoom,
          )
        lastPosition = pos
        cameraState.position = pos
      }
    },
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
