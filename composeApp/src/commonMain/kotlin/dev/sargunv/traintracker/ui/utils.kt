package dev.sargunv.traintracker.ui

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
expect fun getSheetHeight(): Dp

@Composable
expect fun getColorScheme(): ColorScheme
