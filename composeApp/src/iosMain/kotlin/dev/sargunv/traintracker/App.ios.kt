@file:OptIn(
    ExperimentalComposeUiApi::class
)

package dev.sargunv.traintracker

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.UIKitView
import platform.MapKit.MKMapView

@Composable
actual fun getColorScheme(darkMode: Boolean) =
    if (darkMode) darkColorScheme() else lightColorScheme()

@Composable
actual fun getSheetHeight(): Dp {
    val fullWindowHeight = LocalWindowInfo.current.containerSize.height
    val topSafeInset = WindowInsets.safeDrawing.getTop(LocalDensity.current)
    return with(LocalDensity.current) { (fullWindowHeight - topSafeInset).toDp() }
}

@Composable
actual fun NativeMap(darkMode: Boolean) {
    UIKitView(
        factory = {
            MKMapView()
        },
        modifier = Modifier.fillMaxSize(),
    )
}