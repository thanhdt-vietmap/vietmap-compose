package dev.sargunv.maplibrecompose.core

import dev.sargunv.maplibrecompose.core.camera.CameraPosition
import dev.sargunv.maplibrecompose.core.data.GestureSettings
import dev.sargunv.maplibrecompose.core.data.OrnamentSettings
import dev.sargunv.maplibrecompose.core.data.XY
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.Position
import kotlin.time.Duration

internal interface MaplibreMap {
  var styleUrl: String
  var isDebugEnabled: Boolean
  var cameraPosition: CameraPosition

  fun setOrnamentSettings(value: OrnamentSettings)

  fun setGestureSettings(value: GestureSettings)

  suspend fun animateCameraPosition(finalPosition: CameraPosition, duration: Duration)

  fun positionFromScreenLocation(xy: XY): Position

  fun screenLocationFromPosition(position: Position): XY

  fun queryRenderedFeatures(xy: XY, layerIds: Set<String>): List<Feature>

  interface Callbacks {
    fun onStyleChanged(map: MaplibreMap, style: Style?)

    fun onCameraMove(map: MaplibreMap)

    fun onClick(map: MaplibreMap, latLng: Position, xy: XY)

    fun onLongClick(map: MaplibreMap, latLng: Position, xy: XY)
  }
}
