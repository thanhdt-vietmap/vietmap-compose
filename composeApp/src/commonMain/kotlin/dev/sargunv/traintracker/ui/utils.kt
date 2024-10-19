package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

@Composable
expect fun getSheetHeight(): Dp

@Composable
expect fun getColorScheme(): ColorScheme

data class PaddingPxValues(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)

@Composable
fun paddingValuesToPx(paddingValues: PaddingValues): PaddingPxValues {
    val layoutDirection = LocalLayoutDirection.current
    with(LocalDensity.current) {
        val left = paddingValues.calculateLeftPadding(layoutDirection).toPx().roundToInt()
        val top = paddingValues.calculateTopPadding().toPx().roundToInt()
        val right = paddingValues.calculateRightPadding(layoutDirection).toPx().roundToInt()
        val bottom = paddingValues.calculateBottomPadding().toPx().roundToInt()
        return PaddingPxValues(left, top, right, bottom)
    }
}