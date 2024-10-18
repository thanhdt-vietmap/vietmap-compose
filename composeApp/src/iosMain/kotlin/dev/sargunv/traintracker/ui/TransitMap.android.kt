package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.MapKit.MKMapView

@Composable
actual fun TransitMap() {
    UIKitView(
        factory = {
            MKMapView()
        },
        modifier = Modifier.fillMaxSize(),
    )
}