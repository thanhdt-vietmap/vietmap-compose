package dev.sargunv.traintracker

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

@Composable expect fun getSheetHeight(): Dp

@Composable expect fun getColorScheme(): ColorScheme

operator fun PaddingValues.plus(other: PaddingValues) =
  PaddingValues(
    start =
      calculateLeftPadding(LayoutDirection.Ltr) + other.calculateLeftPadding(LayoutDirection.Ltr),
    end =
      calculateRightPadding(LayoutDirection.Ltr) + other.calculateRightPadding(LayoutDirection.Ltr),
    top = calculateTopPadding() + other.calculateTopPadding(),
    bottom = calculateBottomPadding() + other.calculateBottomPadding(),
  )

fun max(a: PaddingValues, b: PaddingValues) =
  PaddingValues(
    start =
      androidx.compose.ui.unit.max(
        a.calculateLeftPadding(LayoutDirection.Ltr),
        b.calculateLeftPadding(LayoutDirection.Ltr),
      ),
    end =
      androidx.compose.ui.unit.max(
        a.calculateRightPadding(LayoutDirection.Ltr),
        b.calculateRightPadding(LayoutDirection.Ltr),
      ),
    top = androidx.compose.ui.unit.max(a.calculateTopPadding(), b.calculateTopPadding()),
    bottom = androidx.compose.ui.unit.max(a.calculateBottomPadding(), b.calculateBottomPadding()),
  )
