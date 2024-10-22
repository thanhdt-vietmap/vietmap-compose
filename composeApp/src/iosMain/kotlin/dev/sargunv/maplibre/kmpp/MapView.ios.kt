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
import cocoapods.MapLibre.MLNLineStyleLayer
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNMapViewDelegateProtocol
import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
import cocoapods.MapLibre.MLNSource
import cocoapods.MapLibre.MLNStyle
import cocoapods.MapLibre.MLNStyleLayer
import cocoapods.MapLibre.allowsTilting
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGPointMake
import platform.Foundation.NSExpression
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.UIKit.UIColor
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

fun Source.GeoJson.toNativeSource(id: String) = MLNShapeSource(
    identifier = id,
    URL = NSURL(string = url),
    options = buildMap {
        tolerance?.let { put(MLNShapeSourceOptionSimplificationTolerance, NSNumber(it.toDouble())) }
    }
)

fun Int.toUiColor(): UIColor {
    return UIColor(
        red = ((this shr 16) and 0xFF).toDouble() / 255.0,
        green = ((this shr 8) and 0xFF).toDouble() / 255.0,
        blue = (this and 0xFF).toDouble() / 255.0,
        alpha = ((this shr 24) and 0xFF).toDouble() / 255.0
    )
}

fun MLNStyleLayer.applyFrom(layer: Layer) {
    layer.minZoom?.let { setMinimumZoomLevel(it) }
    layer.maxZoom?.let { setMaximumZoomLevel(it) }
}

fun Layer.Type.Line.toNativeLayer(
    getSource: (String) -> MLNSource,
    layer: Layer
): MLNLineStyleLayer {
    return MLNLineStyleLayer(layer.id, getSource(layer.source)).apply {
        applyFrom(layer)
        cap?.let { setLineCap(NSExpression.expressionForConstantValue(it)) }
        join?.let { setLineJoin(NSExpression.expressionForConstantValue(it)) }
        color?.let { setLineColor(NSExpression.expressionForConstantValue(it.toUiColor())) }
        width?.let { setLineWidth(NSExpression.expressionForConstantValue(it)) }
    }
}

class MapViewDelegate(
    val getLatestOptions: () -> MapViewOptions
) : NSObject(), MLNMapViewDelegateProtocol {
    override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) {
        val getSource = { id: String ->
            didFinishLoadingStyle.sourceWithIdentifier(id)
                ?: error("Source not found: $id")
        }
        getLatestOptions().style.sources
            .map { (id, source) ->
                when (source) {
                    is Source.GeoJson -> source.toNativeSource(id)
                }
            }
            .forEach { didFinishLoadingStyle.addSource(it) }
        getLatestOptions().style.layers
            .map { layer ->
                when (layer.type) {
                    is Layer.Type.Line -> layer.type.toNativeLayer(getSource, layer)
                }
            }
            .forEach { didFinishLoadingStyle.addLayer(it) }
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
