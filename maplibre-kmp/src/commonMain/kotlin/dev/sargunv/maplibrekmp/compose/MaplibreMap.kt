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
  public val target: LatLng = LatLng(0.0, 0.0),
  public val tilt: Double = 0.0,
  public val zoom: Double = 1.0,
)

@Stable
public class CameraState
internal constructor(firstPosition: CameraPosition, firstPadding: CameraPadding) {
  internal var map: PlatformMap? = null
  public var position: CameraPosition by mutableStateOf(firstPosition)
  public var padding: CameraPadding by mutableStateOf(firstPadding)

  public fun animateTo(finalPosition: CameraPosition) {
    map?.animateCameraPosition(finalPosition)
      ?: error("Map must be initialized before calling animate")
  }

  public fun animateTo(finalPadding: CameraPadding) {
    map?.animateCameraPadding(finalPadding)
      ?: error("Map must be initialized before calling animate")
  }
}

@Composable
public fun rememberCameraState(
  firstPosition: CameraPosition = CameraPosition(),
  firstPadding: CameraPadding = CameraPadding(),
): CameraState = remember {
  CameraState(firstPosition = firstPosition, firstPadding = firstPadding)
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
  var lastPadding by remember { mutableStateOf<CameraPadding?>(null) }
  val position = cameraState.position
  val padding = cameraState.padding

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
        it.cameraPosition = position
        lastPosition = position
      }
      if (padding != lastPadding) {
        it.cameraPadding = padding
        lastPadding = padding
      }
    },
    onMapLoaded = { map = it },
    onStyleLoaded = { style = it },
    onCameraMove = {
      map!!.let {
        val pos = it.cameraPosition
        val pad = it.cameraPadding
        lastPosition = pos
        cameraState.position = pos
        lastPadding = pad
        cameraState.padding = pad
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
