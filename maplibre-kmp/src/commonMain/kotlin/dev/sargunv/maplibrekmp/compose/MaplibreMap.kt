package dev.sargunv.maplibrekmp.compose

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
import dev.sargunv.maplibrekmp.compose.engine.MapNodeApplier
import dev.sargunv.maplibrekmp.compose.engine.StyleNode
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.StyleManager
import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.ExpressionScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.channels.Channel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

public class CameraState internal constructor(firstPosition: CameraPosition) {
  internal var map: PlatformMap? = null
    set(value) {
      field = value
      value?.let { map ->
        map.cameraPosition = position
        mapAttachSignal.trySend(map)
      }
    }

  private val mapAttachSignal = Channel<PlatformMap>()

  internal val positionState = mutableStateOf(firstPosition)

  // the map has its own internal state, so our State here is read-only
  // the setter directly updates the map, which will call onCameraMove and update the State
  // if the map is not yet initialized, we store the value to apply it later
  public var position: CameraPosition
    get() = positionState.value
    set(value) {
      map?.cameraPosition = value
      positionState.value = value
    }

  public suspend fun animateTo(
    finalPosition: CameraPosition,
    duration: Duration = 300.milliseconds,
  ) {
    val map = map ?: mapAttachSignal.receive()
    map.animateCameraPosition(finalPosition, duration)
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

  SideEffect {
    println("Setting cameraState.map to $map")
    cameraState.map = map
  }

  PlatformMapView(
    modifier = modifier,
    uiPadding = uiSettings.uiPadding,
    styleUrl = styleUrl,
    updateMap = {
      it.isDebugEnabled = uiSettings.isDebugEnabled
      it.isLogoEnabled = uiSettings.isLogoEnabled
      it.isAttributionEnabled = uiSettings.isAttributionEnabled
      it.isCompassEnabled = uiSettings.isCompassEnabled
      it.isTiltGesturesEnabled = uiSettings.isTiltGesturesEnabled
      it.isZoomGesturesEnabled = uiSettings.isZoomGesturesEnabled
      it.isRotateGesturesEnabled = uiSettings.isRotateGesturesEnabled
      it.isScrollGesturesEnabled = uiSettings.isScrollGesturesEnabled
    },
    onMapLoaded = { map = it },
    onStyleLoaded = { style = it },
    onCameraMove = { cameraState.positionState.value = map!!.cameraPosition },
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
