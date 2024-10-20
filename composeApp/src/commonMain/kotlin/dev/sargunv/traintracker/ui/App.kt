package dev.sargunv.traintracker.ui

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun App() {
    KoinContext {
        MaterialTheme(colorScheme = getColorScheme()) {
            BottomSheetScaffold(
                sheetPeekHeight = max(128.dp, getSheetHeight() / 4),
                sheetDragHandle = { BottomSheetDragHandle() },
                sheetContainerColor = MaterialTheme.colorScheme.surface,
                sheetContent = { MainMenu() },
                content = { padding -> TrainMap(sheetPadding = padding) }
            )
        }
    }
}

