@file:OptIn(ExperimentalForeignApi::class)

package dev.sargunv.maplibre.kmpp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNMapViewDelegateProtocol
import cocoapods.MapLibre.MLNStyle
import cocoapods.MapLibre.allowsTilting
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGPointMake
import platform.Foundation.NSURL
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.darwin.NSObject


@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    options: MapViewOptions,
) {
    // UIKitView has some long-lived lambdas that need to reference the latest values
    val latestUiOptions by rememberUpdatedState(options.ui)
    val latestStyleOptions by rememberUpdatedState(options.style)
    val latestLayoutDir by rememberUpdatedState(LocalLayoutDirection.current)
    val latestInsetPadding by rememberUpdatedState(WindowInsets.safeDrawing.asPaddingValues())

    UIKitView(
        modifier = modifier.fillMaxSize(),
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative
        ),
        factory = {
            MLNMapView().apply {
                autoresizingMask =
                    UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
                delegate = MapViewDelegate(getLatestOptions = { options })
            }
        },
        update = { mapView ->
            mapView.applyUiOptions(latestUiOptions, latestInsetPadding, latestLayoutDir)
            mapView.setStyleURL(NSURL(string = latestStyleOptions.url))
        },
    )
}

class MapViewDelegate(
    val getLatestOptions: () -> MapViewOptions
) : NSObject(), MLNMapViewDelegateProtocol {
    val options get() = getLatestOptions()
    override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) {
        // no-op
    }
}

fun MLNMapView.applyUiOptions(
    options: MapViewOptions.UiOptions,
    insetPadding: PaddingValues,
    layoutDir: LayoutDirection,
) {
    logoView.setHidden(!options.isLogoEnabled)
    attributionButton.setHidden(!options.isAttributionEnabled)
    compassView.setHidden(!options.isCompassEnabled)

    allowsTilting = options.isTiltGesturesEnabled
    zoomEnabled = options.isZoomGesturesEnabled
    rotateEnabled = options.isRotateGesturesEnabled
    scrollEnabled = options.isScrollGesturesEnabled

    val leftSafeInset = insetPadding.calculateLeftPadding(layoutDir).value
    val rightSafeInset = insetPadding.calculateRightPadding(layoutDir).value
    val bottomSafeInset = insetPadding.calculateBottomPadding().value

    val leftUiPadding = options.padding.calculateLeftPadding(layoutDir).value - leftSafeInset
    val rightUiPadding = options.padding.calculateRightPadding(layoutDir).value - rightSafeInset
    val bottomUiPadding = options.padding.calculateBottomPadding().value - bottomSafeInset

    setLogoViewMargins(
        CGPointMake(
            leftUiPadding.toDouble(),
            bottomUiPadding.toDouble()
        )
    )
    setAttributionButtonMargins(
        CGPointMake(
            rightUiPadding.toDouble(),
            bottomUiPadding.toDouble()
        )
    )
}
