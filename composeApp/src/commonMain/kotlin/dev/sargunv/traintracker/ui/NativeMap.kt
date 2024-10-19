package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
expect fun NativeMap(
    modifier: Modifier = Modifier,
    uiPadding: PaddingValues = PaddingValues(8.dp)
)

