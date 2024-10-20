package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.allowsTilting
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGPointMake
import platform.Foundation.NSURL
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth

@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    styleUrl: String,
    uiSettings: MapUiSettings,
    lines: Set<MapLine>, // TODO
    symbols: Set<MapSymbol>, // TODO
) {
    val insetPadding = WindowInsets.safeDrawing.asPaddingValues()
    val dir = LocalLayoutDirection.current

    UIKitView(
        modifier = modifier.fillMaxSize(),
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative
        ),
        factory = {
            MLNMapView().apply {
                autoresizingMask =
                    UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
            }
        },
        update = { mapView ->
            mapView.setStyleURL(NSURL(string = styleUrl))

            mapView.logoView.setHidden(!uiSettings.isLogoEnabled)
            mapView.attributionButton.setHidden(!uiSettings.isAttributionEnabled)
            mapView.compassView.setHidden(!uiSettings.isCompassEnabled)

            mapView.allowsTilting = uiSettings.isTiltGesturesEnabled
            mapView.zoomEnabled = uiSettings.isZoomGesturesEnabled
            mapView.rotateEnabled = uiSettings.isRotateGesturesEnabled
            mapView.scrollEnabled = uiSettings.isScrollGesturesEnabled

            if (uiSettings.padding != null) {
                val leftSafeInset = insetPadding.calculateLeftPadding(dir).value
                val rightSafeInset = insetPadding.calculateRightPadding(dir).value
                val bottomSafeInset = insetPadding.calculateBottomPadding().value

                val leftUiPadding =
                    uiSettings.padding.calculateLeftPadding(dir).value - leftSafeInset
                val rightUiPadding =
                    uiSettings.padding.calculateRightPadding(dir).value - rightSafeInset
                val bottomUiPadding =
                    uiSettings.padding.calculateBottomPadding().value - bottomSafeInset

                mapView.setLogoViewMargins(
                    CGPointMake(
                        leftUiPadding.toDouble(),
                        bottomUiPadding.toDouble()
                    )
                )
                mapView.setAttributionButtonMargins(
                    CGPointMake(
                        rightUiPadding.toDouble(),
                        bottomUiPadding.toDouble()
                    )
                )
            }

        },
    )
}
