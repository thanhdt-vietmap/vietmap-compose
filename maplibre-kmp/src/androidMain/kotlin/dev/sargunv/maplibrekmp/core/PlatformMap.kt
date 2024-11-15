package dev.sargunv.maplibrekmp.core

import android.graphics.PointF
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration
import kotlin.time.DurationUnit
import org.maplibre.android.camera.CameraPosition as MLNCameraPosition

internal actual class PlatformMap private actual constructor() {
  private lateinit var impl: MapLibreMap
  internal var layoutDirection: LayoutDirection = LayoutDirection.Ltr

  internal constructor(mapView: MapLibreMap) : this() {
    impl = mapView
  }

  actual var isDebugEnabled
    get() = impl.isDebugActive
    set(value) {
      impl.isDebugActive = value
    }

  actual var isLogoEnabled
    get() = impl.uiSettings.isLogoEnabled
    set(value) {
      impl.uiSettings.isLogoEnabled = value
    }

  actual var isAttributionEnabled
    get() = impl.uiSettings.isAttributionEnabled
    set(value) {
      impl.uiSettings.isAttributionEnabled = value
    }

  actual var isCompassEnabled
    get() = impl.uiSettings.isCompassEnabled
    set(value) {
      impl.uiSettings.isCompassEnabled = value
    }

  actual var isRotateGesturesEnabled
    get() = impl.uiSettings.isRotateGesturesEnabled
    set(value) {
      impl.uiSettings.isRotateGesturesEnabled = value
    }

  actual var isScrollGesturesEnabled
    get() = impl.uiSettings.isScrollGesturesEnabled
    set(value) {
      impl.uiSettings.isScrollGesturesEnabled = value
    }

  actual var isTiltGesturesEnabled
    get() = impl.uiSettings.isTiltGesturesEnabled
    set(value) {
      impl.uiSettings.isTiltGesturesEnabled = value
    }

  actual var isZoomGesturesEnabled
    get() = impl.uiSettings.isZoomGesturesEnabled
    set(value) {
      impl.uiSettings.isZoomGesturesEnabled = value
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
      .target(LatLng(target.latitude, target.longitude))
      .zoom(zoom)
      .tilt(tilt)
      .bearing(bearing)
      .padding(
        left = padding.calculateLeftPadding(layoutDirection).value.toDouble(),
        top = padding.calculateTopPadding().value.toDouble(),
        right = padding.calculateRightPadding(layoutDirection).value.toDouble(),
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

  actual fun queryRenderedFeatures(
    xy: Pair<Float, Float>,
    layerIds: Set<String>,
  ): List<Feature> =
    impl.queryRenderedFeatures(PointF(xy.first, xy.second), *layerIds.toTypedArray()).map {
      Feature.fromJson(it.toJson())
    }
}
