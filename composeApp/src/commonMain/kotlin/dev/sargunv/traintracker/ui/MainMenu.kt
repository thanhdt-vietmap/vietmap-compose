package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu() {
  Column(
      modifier =
          Modifier
              // subtract drag handle height
              .heightIn(max = getSheetHeight() - 26.dp)
              // the max sheet height already accounts for the top inset
              .consumeWindowInsets(
                  WindowInsets(top = WindowInsets.safeDrawing.getTop(LocalDensity.current)))
              .verticalScroll(rememberScrollState()),
  ) {
    CenterAlignedTopAppBar(
        title = { Text("My Trains") },
        actions = {
          IconButton(onClick = { /*TODO*/ }) { Icon(Icons.Default.Add, contentDescription = "Add") }
          IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
          }
        })
    for (i in 0..20) {
      Text(
          text = "Item $i",
          style =
              MaterialTheme.typography.bodyMedium.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant),
          modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center).padding(16.dp))
    }
  }
}
