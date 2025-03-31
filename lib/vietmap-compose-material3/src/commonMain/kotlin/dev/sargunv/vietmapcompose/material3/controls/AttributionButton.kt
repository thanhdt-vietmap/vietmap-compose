package dev.sargunv.vietmapcompose.material3.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import dev.sargunv.vietmapcompose.compose.StyleState
import dev.sargunv.vietmapcompose.material3.generated.Res
import dev.sargunv.vietmapcompose.material3.generated.attribution
import dev.sargunv.vietmapcompose.material3.generated.library_name
import org.jetbrains.compose.resources.stringResource

@Composable
public fun AttributionButton(
  styleState: StyleState,
  modifier: Modifier = Modifier,
  colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
) {
  var showDialog by remember { mutableStateOf(false) }

  IconButton(modifier = modifier, colors = colors, onClick = { showDialog = true }) {
    Icon(Icons.Outlined.Info, contentDescription = stringResource(Res.string.attribution))
  }

  if (showDialog) {
    val attributions = remember(styleState) { styleState.queryAttributionLinks() }
    val uriHandler = LocalUriHandler.current

    AlertDialog(
      onDismissRequest = { showDialog = false },
      confirmButton = {},
      title = {
        Text(
          text = stringResource(Res.string.library_name),
          style = MaterialTheme.typography.headlineSmall,
        )
      },
      text = {
        Column {
          attributions.forEach {
            TextButton(onClick = { uriHandler.openUri(it.url) }) { Text(text = it.title) }
          }
        }
      },
    )
  }
}
