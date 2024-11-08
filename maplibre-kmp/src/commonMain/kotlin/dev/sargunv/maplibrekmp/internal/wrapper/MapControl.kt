package dev.sargunv.maplibrekmp.internal.wrapper

internal expect class MapControl private constructor() {
  var isDebugEnabled: Boolean
  var isLogoEnabled: Boolean
  var isAttributionEnabled: Boolean
  var isCompassEnabled: Boolean
  var isRotateGesturesEnabled: Boolean
  var isScrollGesturesEnabled: Boolean
  var isTiltGesturesEnabled: Boolean
  var isZoomGesturesEnabled: Boolean
}
