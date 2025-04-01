package vn.vietmap.vietmapcompose.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import vn.vietmap.vietmapcompose.core.VietMapGLCompose

@Composable
internal expect fun ComposableMapView(
    modifier: Modifier,
    styleUri: String,
    update: (map: VietMapGLCompose) -> Unit,
    onReset: () -> Unit,
    logger: Logger?,
    callbacks: VietMapGLCompose.Callbacks,
)
