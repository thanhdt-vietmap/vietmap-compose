@file:OptIn(ExperimentalForeignApi::class)

package dev.sargunv.traintracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.MapKit.MKMapView

@Composable
actual fun NativeMap(darkMode: Boolean) {
    UIKitView(
        factory = {
            MKMapView()
        },
        modifier = Modifier.fillMaxSize()
    )
}