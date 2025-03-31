package dev.sargunv.vietmapcompose.demoapp.demos

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.sargunv.vietmapcompose.compose.CameraState
import dev.sargunv.vietmapcompose.compose.MaplibreMap
import dev.sargunv.vietmapcompose.compose.layer.CircleLayer
import dev.sargunv.vietmapcompose.compose.rememberCameraState
import dev.sargunv.vietmapcompose.compose.rememberStyleState
import dev.sargunv.vietmapcompose.compose.source.rememberGeoJsonSource
import dev.sargunv.vietmapcompose.core.CameraMoveReason
import dev.sargunv.vietmapcompose.core.CameraPosition
import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.demoapp.DEFAULT_STYLE
import dev.sargunv.vietmapcompose.demoapp.Demo
import dev.sargunv.vietmapcompose.demoapp.DemoMapControls
import dev.sargunv.vietmapcompose.demoapp.DemoOrnamentSettings
import dev.sargunv.vietmapcompose.demoapp.DemoScaffold
import dev.sargunv.vietmapcompose.demoapp.Platform
import dev.sargunv.vietmapcompose.demoapp.PositionVectorConverter
import dev.sargunv.vietmapcompose.demoapp.supportsLayers
import dev.sargunv.vietmapcompose.expressions.dsl.const
import dev.sargunv.vietmapcompose.expressions.dsl.offset
import io.github.dellisd.spatialk.geojson.Point
import io.github.dellisd.spatialk.geojson.Position
import kotlin.math.roundToInt

private val START_POINT = Position(longitude = -122.4194, latitude = 37.7749)
private val END_POINT = Position(longitude = -122.3954, latitude = 37.7939)
private const val MIN_ZOOM = 9
private const val MAX_ZOOM = 15

object CameraFollowDemo : Demo {
  override val name = "Camera follow"
  override val description = "Make the camera follow a point on the map."

  @Composable
  override fun Component(navigateUp: () -> Unit) {
    DemoScaffold(this, navigateUp) {
      Column {
        val animatedPosition by animateTestPosition(START_POINT, END_POINT)
        var followAtZoom by remember { mutableStateOf((MIN_ZOOM + MAX_ZOOM) / 2) }
        var isFollowing by remember { mutableStateOf(true) }

        val cameraState =
          rememberAnimatedFollowCamera(
            targetPos = animatedPosition,
            targetZoom = followAtZoom.toFloat(),
            isFollowing = isFollowing,
          )
        val styleState = rememberStyleState()

        LaunchedEffect(cameraState.moveReason) {
          if (cameraState.moveReason == CameraMoveReason.GESTURE) {
            isFollowing = false
            cameraState.position = cameraState.position.copy()
          }
        }

        Box(modifier = Modifier.weight(1f)) {
          MaplibreMap(
            styleUri = DEFAULT_STYLE,
            cameraState = cameraState,
            styleState = styleState,
            ornamentSettings = DemoOrnamentSettings(),
          ) {
            if (Platform.supportsLayers) {
              LocationPuck(
                locationSource = rememberGeoJsonSource("target", Point(animatedPosition))
              )
            }
          }
          DemoMapControls(
            cameraState,
            styleState,
            onCompassClick = {
              isFollowing = false
              cameraState.position = cameraState.position.copy()
            },
          )
        }

        Text(
          text = "Move reason: ${cameraState.moveReason.name}",
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth(),
        )

        FollowControls(
          currentZoom = followAtZoom,
          isFollowing = isFollowing,
          onZoomChange = { followAtZoom = it },
          onStartFollowing = {
            isFollowing = true
            followAtZoom = cameraState.position.zoom.roundToInt().coerceIn(MIN_ZOOM, MAX_ZOOM)
          },
        )
      }
    }
  }
}

@Composable
private fun animateTestPosition(start: Position, end: Position): State<Position> {
  val infiniteTransition = rememberInfiniteTransition()
  return infiniteTransition.animateValue(
    start,
    end,
    typeConverter = remember { PositionVectorConverter(origin = START_POINT) },
    animationSpec =
      remember {
        infiniteRepeatable(
          animation = tween(durationMillis = 30_000),
          repeatMode = RepeatMode.Reverse,
        )
      },
  )
}

@Composable
private fun rememberAnimatedFollowCamera(
  targetPos: Position,
  targetZoom: Float,
  isFollowing: Boolean,
): CameraState {
  val cameraState =
    rememberCameraState(
      firstPosition = CameraPosition(target = targetPos, zoom = targetZoom.toDouble())
    )

  LaunchedEffect(isFollowing, targetPos, targetZoom) {
    if (isFollowing)
      cameraState.animateTo(
        cameraState.position.copy(target = targetPos, zoom = targetZoom.toDouble())
      )
  }

  return cameraState
}

@Composable
private fun LocationPuck(locationSource: Source) {
  CircleLayer(
    id = "target-shadow",
    source = locationSource,
    radius = const(13.dp),
    color = const(Color.Black),
    blur = const(1f),
    translate = offset(0.dp, 1.dp),
  )

  CircleLayer(
    id = "target-circle",
    source = locationSource,
    radius = const(7.dp),
    color = const(MaterialTheme.colorScheme.primary),
    strokeColor = const(Color.White),
    strokeWidth = const(3.dp),
  )
}

@Composable
private fun FollowControls(
  currentZoom: Int,
  isFollowing: Boolean,
  onZoomChange: (Int) -> Unit,
  onStartFollowing: () -> Unit,
) {
  Row(
    modifier = Modifier.padding(16.dp).fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly,
  ) {
    Button(
      enabled = isFollowing && currentZoom > MIN_ZOOM,
      onClick = { onZoomChange((currentZoom - 1).coerceAtLeast(MIN_ZOOM)) },
    ) {
      Text("Zoom out")
    }
    Button(enabled = !isFollowing, onClick = onStartFollowing) { Text("Follow") }
    Button(
      enabled = isFollowing && currentZoom < MAX_ZOOM,
      onClick = { onZoomChange((currentZoom + 1).coerceAtMost(MAX_ZOOM)) },
    ) {
      Text("Zoom in")
    }
  }
}
