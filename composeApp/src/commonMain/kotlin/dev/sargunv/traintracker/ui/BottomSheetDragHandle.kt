package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetDragHandle() {
    Surface(
        // no bottom padding to avoid double padding with the TopAppBar
        modifier = Modifier.padding(top = 22.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(Modifier.size(width = 32.dp, height = 4.dp))
    }
}