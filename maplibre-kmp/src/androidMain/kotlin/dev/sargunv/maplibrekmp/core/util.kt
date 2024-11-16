package dev.sargunv.maplibrekmp.core

import android.graphics.PointF
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.android.geometry.LatLng
import java.net.URI

internal fun String.correctedAndroidUri(): URI {
  val uri = URI(this)
  return if (uri.scheme == "file" && uri.path.startsWith("/android_asset/")) {
    URI("asset://${uri.path.removePrefix("/android_asset/")}")
  } else {
    uri
  }
}

internal fun XY.toPointF(): PointF = PointF(x, y)

internal fun PointF.toXY(): XY = XY(x = x, y = y)

internal fun LatLng.toPosition(): Position = Position(longitude = longitude, latitude = latitude)

internal fun Position.toLatLng(): LatLng = LatLng(latitude = latitude, longitude = longitude)
