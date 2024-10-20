package dev.sargunv.traintracker

import androidx.compose.ui.window.ComposeUIViewController
import dev.sargunv.traintracker.ui.App

@Suppress("unused", "FunctionName") // called in Swift
fun MainViewController() = ComposeUIViewController { App() }