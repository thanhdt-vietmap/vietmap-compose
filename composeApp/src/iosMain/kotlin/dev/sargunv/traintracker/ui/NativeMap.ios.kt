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
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGPointMake
import platform.Foundation.NSURL
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth

@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
@Composable
actual fun NativeMap(modifier: Modifier, uiPadding: PaddingValues) {
    val leftUiPadding = uiPadding.calculateLeftPadding(LocalLayoutDirection.current).value
    val rightUiPadding = uiPadding.calculateRightPadding(LocalLayoutDirection.current).value
    val bottomSafeInset = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding().value
    val bottomUiPadding = uiPadding.calculateBottomPadding().value - bottomSafeInset

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
            mapView.setStyleURL(NSURL(string = "https://tiles.openfreemap.org/styles/positron"))
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

//                setAttributionMargins(
//                    leftUiPadding + logoWidth,
//                    topUiPadding,
//                    rightUiPadding,
//                    bottomUiPadding
//                )
//                setLogoMargins(
//                    leftUiPadding,
//                    topUiPadding,
//                    rightUiPadding,
//                    bottomUiPadding
//                )
        }
    )
}
