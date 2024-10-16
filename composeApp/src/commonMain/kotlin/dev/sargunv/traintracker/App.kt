package dev.sargunv.traintracker

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        NativeMap()
    }
}

@Composable
expect fun NativeMap()