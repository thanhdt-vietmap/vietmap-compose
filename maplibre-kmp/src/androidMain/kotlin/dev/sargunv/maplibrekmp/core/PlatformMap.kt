package dev.sargunv.maplibrekmp.core

import android.graphics.PointF
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration
import kotlin.time.DurationUnit
import org.maplibre.android.camera.CameraPosition as MLNCameraPosition

internal actual class PlatformMap private actual constructor() {
  internal lateinit var mapView: MapView
  internal lateinit var mapLibreMap: MapLibreMap
  internal lateinit var layoutDir: LayoutDirection

  internal var onCameraMove: () -> Unit = {}
  internal var onClick: (Position, XY) -> Unit = { _, _ -> }
  internal var onLongClick: (Position, XY) -> Unit = { _, _ -> }

  internal constructor(mapView: MapView, map: MapLibreMap, layoutDir: LayoutDirection) : this() {
    this.mapView = mapView
    this.mapLibreMap = map
    this.layoutDir = layoutDir

    map.addOnCameraMoveListener { onCameraMove() }
    map.addOnMapClickListener { coords ->
      val pos = coords.toPosition()
      onClick(pos, screenLocationFromPosition(pos))
      true
    }
    map.addOnMapLongClickListener { coords ->
      val pos = coords.toPosition()
      onLongClick(pos, screenLocationFromPosition(pos))
      true
    }
  }

  actual var isDebugEnabled
    get() = mapLibreMap.isDebugActive
    set(value) {
      mapLibreMap.isDebugActive = value
    }

  actual var controlSettings
    get() =
      ControlSettings(
        isLogoEnabled = mapLibreMap.uiSettings.isLogoEnabled,
        isAttributionEnabled = mapLibreMap.uiSettings.isAttributionEnabled,
        isCompassEnabled = mapLibreMap.uiSettings.isCompassEnabled,
        isRotateGesturesEnabled = mapLibreMap.uiSettings.isRotateGesturesEnabled,
        isScrollGesturesEnabled = mapLibreMap.uiSettings.isScrollGesturesEnabled,
        isTiltGesturesEnabled = mapLibreMap.uiSettings.isTiltGesturesEnabled,
        isZoomGesturesEnabled = mapLibreMap.uiSettings.isZoomGesturesEnabled,
      )
    set(value) {
      mapLibreMap.uiSettings.isLogoEnabled = value.isLogoEnabled
      mapLibreMap.uiSettings.isAttributionEnabled = value.isAttributionEnabled
      mapLibreMap.uiSettings.isCompassEnabled = value.isCompassEnabled
      mapLibreMap.uiSettings.isRotateGesturesEnabled = value.isRotateGesturesEnabled
      mapLibreMap.uiSettings.isScrollGesturesEnabled = value.isScrollGesturesEnabled
      mapLibreMap.uiSettings.isTiltGesturesEnabled = value.isTiltGesturesEnabled
      mapLibreMap.uiSettings.isZoomGesturesEnabled = value.isZoomGesturesEnabled
    }

  private fun MLNCameraPosition.toCameraPosition(): CameraPosition =
    CameraPosition(
      target = target?.let { Position(it.latitude, it.longitude) } ?: Position(0.0, 0.0),
      zoom = zoom,
      bearing = bearing,
      tilt = tilt,
      padding =
        padding?.let {
          PaddingValues.Absolute(
            left = it[0].dp,
            top = it[1].dp,
            right = it[2].dp,
            bottom = it[3].dp,
          )
        } ?: PaddingValues.Absolute(0.dp),
    )

  private fun CameraPosition.toMLNCameraPosition(): MLNCameraPosition =
    MLNCameraPosition.Builder()
      .target(target.toLatLng())
      .zoom(zoom)
      .tilt(tilt)
      .bearing(bearing)
      .padding(
        left = padding.calculateLeftPadding(layoutDir).value.toDouble(),
        top = padding.calculateTopPadding().value.toDouble(),
        right = padding.calculateRightPadding(layoutDir).value.toDouble(),
        bottom = padding.calculateBottomPadding().value.toDouble(),
      )
      .build()

  actual var cameraPosition: CameraPosition
    get() = mapLibreMap.cameraPosition.toCameraPosition()
    set(value) {
      mapLibreMap.moveCamera(CameraUpdateFactory.newCameraPosition(value.toMLNCameraPosition()))
    }

  actual suspend fun animateCameraPosition(finalPosition: CameraPosition, duration: Duration) =
    suspendCoroutine { cont ->
      mapLibreMap.animateCamera(
        CameraUpdateFactory.newCameraPosition(finalPosition.toMLNCameraPosition()),
        duration.toInt(DurationUnit.MILLISECONDS),
        object : MapLibreMap.CancelableCallback {
          override fun onFinish() = cont.resume(Unit)

          override fun onCancel() = cont.resume(Unit)
        },
      )
    }

  actual fun positionFromScreenLocation(xy: XY): Position =
    mapLibreMap.projection.fromScreenLocation(PointF(xy.x, xy.y)).toPosition()

  actual fun screenLocationFromPosition(position: Position): XY =
    mapLibreMap.projection.toScreenLocation(position.toLatLng()).toXY()

  actual fun queryRenderedFeatures(xy: XY, layerIds: Set<String>): List<Feature> =
    mapLibreMap.queryRenderedFeatures(xy.toPointF(), *layerIds.toTypedArray()).map {
      Feature.fromJson(it.toJson())
    }
}
