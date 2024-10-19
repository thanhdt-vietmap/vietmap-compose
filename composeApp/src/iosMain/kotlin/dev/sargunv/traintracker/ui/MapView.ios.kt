package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
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
    uiPadding: PaddingValues?,
    styleUrl: String,
    isLogoEnabled: Boolean,
    isAttributionEnabled: Boolean,
    isCompassEnabled: Boolean,
    isTiltGesturesEnabled: Boolean,
    isZoomGesturesEnabled: Boolean,
    isRotateGesturesEnabled: Boolean,
    isScrollGesturesEnabled: Boolean,
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

            mapView.logoView.setHidden(!isLogoEnabled)
            mapView.attributionButton.setHidden(!isAttributionEnabled)
            mapView.compassView.setHidden(!isCompassEnabled)

            mapView.allowsTilting = isTiltGesturesEnabled
            mapView.zoomEnabled = isZoomGesturesEnabled
            mapView.rotateEnabled = isRotateGesturesEnabled
            mapView.scrollEnabled = isScrollGesturesEnabled

            if (uiPadding != null) {
                val leftSafeInset = insetPadding.calculateLeftPadding(dir).value
                val rightSafeInset = insetPadding.calculateRightPadding(dir).value
                val bottomSafeInset = insetPadding.calculateBottomPadding().value

                val leftUiPadding = uiPadding.calculateLeftPadding(dir).value - leftSafeInset
                val rightUiPadding = uiPadding.calculateRightPadding(dir).value - rightSafeInset
                val bottomUiPadding = uiPadding.calculateBottomPadding().value - bottomSafeInset

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
