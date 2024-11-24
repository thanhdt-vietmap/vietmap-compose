package dev.sargunv.maplibrecompose.demoapp

import dev.sargunv.maplibrecompose.demoapp.generated.Res
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
