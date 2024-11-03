package dev.sargunv.maplibrecompose

import java.net.URI

internal fun String.correctedAndroidUri(): URI {
  val uri = URI(this)
  return if (uri.scheme == "file" && uri.path.startsWith("/android_asset/")) {
    URI("asset://${uri.path.removePrefix("/android_asset/")}")
  } else {
    uri
  }
}
