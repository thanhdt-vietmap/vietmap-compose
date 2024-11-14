package dev.sargunv.maplibrekmp.core

internal expect class PlatformMap private constructor() {
  var isDebugEnabled: Boolean
  var isLogoEnabled: Boolean
  var isAttributionEnabled: Boolean
  var isCompassEnabled: Boolean
  var isRotateGesturesEnabled: Boolean
  var isScrollGesturesEnabled: Boolean
  var isTiltGesturesEnabled: Boolean
  var isZoomGesturesEnabled: Boolean
  var cameraBearing: Double
  var cameraPadding: CameraPadding
  var cameraTarget: LatLng
  var cameraTilt: Double
  var cameraZoom: Double
}
