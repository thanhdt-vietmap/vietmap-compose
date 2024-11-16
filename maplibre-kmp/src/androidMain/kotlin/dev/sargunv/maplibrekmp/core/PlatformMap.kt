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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration
import kotlin.time.DurationUnit
import org.maplibre.android.camera.CameraPosition as MLNCameraPosition

internal actual class PlatformMap private actual constructor() {
  internal lateinit var impl: MapLibreMap
  internal lateinit var layoutDir: LayoutDirection

  internal constructor(mapView: MapLibreMap, layoutDir: LayoutDirection) : this() {
    this.impl = mapView
    this.layoutDir = layoutDir
  }

  actual var isDebugEnabled
    get() = impl.isDebugActive
    set(value) {
      impl.isDebugActive = value
    }

  actual var controlSettings
    get() =
      ControlSettings(
        isLogoEnabled = impl.uiSettings.isLogoEnabled,
        isAttributionEnabled = impl.uiSettings.isAttributionEnabled,
        isCompassEnabled = impl.uiSettings.isCompassEnabled,
        isRotateGesturesEnabled = impl.uiSettings.isRotateGesturesEnabled,
        isScrollGesturesEnabled = impl.uiSettings.isScrollGesturesEnabled,
        isTiltGesturesEnabled = impl.uiSettings.isTiltGesturesEnabled,
        isZoomGesturesEnabled = impl.uiSettings.isZoomGesturesEnabled,
      )
    set(value) {
      impl.uiSettings.isLogoEnabled = value.isLogoEnabled
      impl.uiSettings.isAttributionEnabled = value.isAttributionEnabled
      impl.uiSettings.isCompassEnabled = value.isCompassEnabled
      impl.uiSettings.isRotateGesturesEnabled = value.isRotateGesturesEnabled
      impl.uiSettings.isScrollGesturesEnabled = value.isScrollGesturesEnabled
      impl.uiSettings.isTiltGesturesEnabled = value.isTiltGesturesEnabled
      impl.uiSettings.isZoomGesturesEnabled = value.isZoomGesturesEnabled
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
    get() = impl.cameraPosition.toCameraPosition()
    set(value) {
      impl.moveCamera(CameraUpdateFactory.newCameraPosition(value.toMLNCameraPosition()))
    }

  actual suspend fun animateCameraPosition(finalPosition: CameraPosition, duration: Duration) =
    suspendCoroutine { cont ->
      impl.animateCamera(
        CameraUpdateFactory.newCameraPosition(finalPosition.toMLNCameraPosition()),
        duration.toInt(DurationUnit.MILLISECONDS),
        object : MapLibreMap.CancelableCallback {
          override fun onFinish() = cont.resume(Unit)

          override fun onCancel() = cont.resume(Unit)
        },
      )
    }

  actual fun positionFromScreenLocation(xy: XY): Position =
    impl.projection.fromScreenLocation(PointF(xy.x, xy.y)).toPosition()

  actual fun screenLocationFromPosition(position: Position): XY =
    impl.projection.toScreenLocation(position.toLatLng()).toXY()

  actual fun queryRenderedFeatures(xy: XY, layerIds: Set<String>): List<Feature> =
    impl.queryRenderedFeatures(xy.toPointF(), *layerIds.toTypedArray()).map {
      Feature.fromJson(it.toJson())
    }
}
