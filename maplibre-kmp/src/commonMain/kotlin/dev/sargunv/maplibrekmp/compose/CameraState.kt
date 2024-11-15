package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import kotlinx.coroutines.channels.Channel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
public fun rememberCameraState(firstPosition: CameraPosition = CameraPosition()): CameraState {
  return remember { CameraState(firstPosition) }
}

public class CameraState internal constructor(firstPosition: CameraPosition) {
  internal var map: PlatformMap? = null
    set(map) {
      if (map != field && map != null) {
        map.cameraPosition = position
        mapAttachSignal.trySend(map)
      }
      field = map
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
