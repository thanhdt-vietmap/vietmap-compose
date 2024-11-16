package dev.sargunv.maplibrekmp.core

import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.Position
import kotlin.time.Duration

internal expect class PlatformMap private constructor() {

  var isDebugEnabled: Boolean
  var uiSettings: UiSettings

  var cameraPosition: CameraPosition

  suspend fun animateCameraPosition(finalPosition: CameraPosition, duration: Duration)

  fun positionFromScreenLocation(xy: XY): Position

  fun screenLocationFromPosition(position: Position): XY

  fun queryRenderedFeatures(xy: XY, layerIds: Set<String>): List<Feature>
}
