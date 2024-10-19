package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
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

fun paddingValuesToPx(
    paddingValues: PaddingValues,
    layoutDirection: LayoutDirection,
    density: Density
): PaddingPxValues = with(density) {
    val left = paddingValues.calculateLeftPadding(layoutDirection).toPx().roundToInt()
    val top = paddingValues.calculateTopPadding().toPx().roundToInt()
    val right = paddingValues.calculateRightPadding(layoutDirection).toPx().roundToInt()
    val bottom = paddingValues.calculateBottomPadding().toPx().roundToInt()
    PaddingPxValues(left, top, right, bottom)
}