@file:OptIn(ExperimentalMaterial3Api::class)

package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import dev.sargunv.traintracker.IPlatform
import org.koin.compose.koinInject

@Composable
fun MainScreen() {
    BottomSheetScaffold(
        sheetPeekHeight = max(128.dp, getSheetHeight() / 4),
        sheetDragHandle = { SheetDragHandle() },
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetContent = { SheetContent() },
        content = { _ -> TransitMap() }
    )
}

@Composable
fun SheetContent() {
    Column(
        modifier = Modifier
            .heightIn(
                // subtract drag handle height
                max = getSheetHeight() - 26.dp
            )
            .consumeWindowInsets(
                WindowInsets(
                    // the max sheet height already accounts for the top inset
                    top = WindowInsets.safeDrawing.getTop(LocalDensity.current)
                )
            )
            .verticalScroll(rememberScrollState()),

        ) {
        CenterAlignedTopAppBar(
            title = { Text("My Trains") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        )
        Text(
//            text = "Browse the map or click the + button to get started",
            text = koinInject<IPlatform>().name,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(16.dp)
        )
    }
}

@Composable
fun SheetDragHandle() {
    Surface(
        // no bottom padding to avoid double padding with the TopAppBar
        modifier = Modifier.padding(top = 22.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(Modifier.size(width = 32.dp, height = 4.dp))
    }
}