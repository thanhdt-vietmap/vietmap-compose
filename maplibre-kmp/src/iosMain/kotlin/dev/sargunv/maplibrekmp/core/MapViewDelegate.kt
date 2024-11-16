package dev.sargunv.maplibrekmp.core

import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNMapViewDelegateProtocol
import cocoapods.MapLibre.MLNStyle
import platform.darwin.NSObject

internal class MapViewDelegate(
  private val onStyleLoaded: (style: MLNStyle) -> Unit,
  private val onCameraMove: () -> Unit,
) : NSObject(), MLNMapViewDelegateProtocol {

  override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) =
    onStyleLoaded(didFinishLoadingStyle)

  override fun mapViewRegionIsChanging(mapView: MLNMapView) = onCameraMove()
}
