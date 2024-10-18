package dev.sargunv.traintracker.ui

import android.os.Build
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun getColorScheme(darkMode: Boolean): ColorScheme {
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    return when {
        dynamicColor && darkMode -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !darkMode -> dynamicLightColorScheme(LocalContext.current)
        darkMode -> darkColorScheme()
        else -> lightColorScheme()
    }
}

@Composable
actual fun getSheetHeight(): Dp {
    val safeWindowHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomSafeInset = WindowInsets.safeDrawing.getBottom(LocalDensity.current)
    return with(LocalDensity.current) { safeWindowHeight + bottomSafeInset.toDp() }
}
