package dev.sargunv.maplibrekmp.core

import dev.sargunv.maplibrekmp.compose.CameraPosition

internal expect class PlatformMap private constructor() {

  var isDebugEnabled: Boolean
  var isLogoEnabled: Boolean
  var isAttributionEnabled: Boolean
  var isCompassEnabled: Boolean
  var isRotateGesturesEnabled: Boolean
  var isScrollGesturesEnabled: Boolean
  var isTiltGesturesEnabled: Boolean
  var isZoomGesturesEnabled: Boolean

  var cameraPosition: CameraPosition

  fun animateCameraPosition(finalPosition: CameraPosition)

  var cameraPadding: CameraPadding

  fun animateCameraPadding(finalPadding: CameraPadding)
}
