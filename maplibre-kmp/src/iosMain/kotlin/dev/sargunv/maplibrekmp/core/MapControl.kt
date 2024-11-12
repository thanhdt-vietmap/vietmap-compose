package dev.sargunv.maplibrekmp.core

import cocoapods.MapLibre.MLNMapDebugCollisionBoxesMask
import cocoapods.MapLibre.MLNMapDebugTileBoundariesMask
import cocoapods.MapLibre.MLNMapDebugTileInfoMask
import cocoapods.MapLibre.MLNMapDebugTimestampsMask
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.allowsTilting

internal actual class MapControl private actual constructor() {
  private lateinit var impl: MLNMapView

  internal constructor(impl: MLNMapView) : this() {
    this.impl = impl
  }

  actual var isDebugEnabled: Boolean
    get() = impl.debugMask != 0uL
    set(value) {
      impl.debugMask = if (value) MAP_DEBUG_VALUE else 0uL
    }

  actual var isLogoEnabled: Boolean
    get() = !impl.logoView.hidden
    set(value) {
      impl.logoView.hidden = !value
    }

  actual var isAttributionEnabled: Boolean
    get() = !impl.attributionButton.hidden
    set(value) {
      impl.attributionButton.hidden = !value
    }

  actual var isCompassEnabled: Boolean
    get() = !impl.compassView.hidden
    set(value) {
      impl.compassView.hidden = !value
    }

  actual var isRotateGesturesEnabled: Boolean
    get() = impl.rotateEnabled
    set(value) {
      impl.rotateEnabled = value
    }

  actual var isScrollGesturesEnabled: Boolean
    get() = impl.scrollEnabled
    set(value) {
      impl.scrollEnabled = value
    }

  actual var isTiltGesturesEnabled: Boolean
    get() = impl.allowsTilting
    set(value) {
      impl.allowsTilting = value
    }

  actual var isZoomGesturesEnabled: Boolean
    get() = impl.zoomEnabled
    set(value) {
      impl.zoomEnabled = value
    }

  companion object {
    val MAP_DEBUG_VALUE =
      MLNMapDebugTileBoundariesMask or
        MLNMapDebugTileInfoMask or
        MLNMapDebugTimestampsMask or
        MLNMapDebugCollisionBoxesMask
  }
}
