package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.toSize

@Composable
internal fun MeasuredBox(modifier: Modifier, contents: @Composable (measuredSize: DpSize) -> Unit) {
  val density = LocalDensity.current
  var currentSize by remember { mutableStateOf(DpSize.Unspecified) }
  Box(
    modifier = modifier.onSizeChanged { currentSize = with(density) { it.toSize().toDpSize() } }
  ) {
    if (currentSize.isSpecified) contents(currentSize)
  }
}
