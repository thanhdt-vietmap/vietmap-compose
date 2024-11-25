package dev.sargunv.maplibrecompose.demoapp

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter
import dev.sargunv.maplibrecompose.demoapp.generated.Res
import io.github.dellisd.spatialk.geojson.Position
import org.jetbrains.compose.resources.ExperimentalResourceApi

private val REMOTE_STYLE_URLS =
  listOf(
    "Bright" to "https://tiles.openfreemap.org/styles/bright",
    "Liberty" to "https://tiles.openfreemap.org/styles/liberty",
    "Positron" to "https://tiles.openfreemap.org/styles/positron",
    "Fiord" to "https://tiles.openfreemap.org/styles/fiord",
    "Dark" to "https://tiles.openfreemap.org/styles/dark",
  )

// TODO demo some local styles
private val LOCAL_STYLE_PATHS = emptyList<Pair<String, String>>()

val DEFAULT_STYLE = REMOTE_STYLE_URLS[0].second

@OptIn(ExperimentalResourceApi::class)
fun getAllStyleUrls() =
  REMOTE_STYLE_URLS + LOCAL_STYLE_PATHS.map { it.first to Res.getUri(it.second) }

/** Caution: this converter results in a loss of precision. */
object PositionVectorConverter : TwoWayConverter<Position, AnimationVector2D> {
  override val convertFromVector: (AnimationVector2D) -> Position = { vector ->
    Position(longitude = vector.v1.toDouble(), latitude = vector.v2.toDouble())
  }

  override val convertToVector: (Position) -> AnimationVector2D = { pos ->
    AnimationVector2D(pos.longitude.toFloat(), pos.latitude.toFloat())
  }
}
