package dev.sargunv.traintracker.ui

import android.view.Gravity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.MapLibre
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.plugins.annotation.Line
import org.maplibre.android.plugins.annotation.LineManager
import org.maplibre.android.plugins.annotation.LineOptions
import org.maplibre.android.plugins.annotation.Symbol
import org.maplibre.android.plugins.annotation.SymbolManager
import org.maplibre.android.plugins.annotation.SymbolOptions
import kotlin.math.roundToInt

@Composable
actual fun MapView(
    modifier: Modifier,
    styleUrl: String,
    uiSettings: MapUiSettings,
    lines: Set<MapLine>,
    symbols: Set<MapSymbol>
) {
    // remember some objects related to the underlying MapView, set in the factory
    var observer by remember { mutableStateOf<LifecycleEventObserver?>(null) }
    var lineManager by remember { mutableStateOf<LineManager?>(null) }
    var symbolManager by remember { mutableStateOf<SymbolManager?>(null) }

    // remember the latest values of the parameters, as they'll be used in the update lambda
    val updatedStyleUrl by rememberUpdatedState(styleUrl)
    val updatedUiSettings by rememberUpdatedState(uiSettings)
    val updatedLines by rememberUpdatedState(lines)
    val updatedSymbols by rememberUpdatedState(symbols)

    // remember the annotations that have already been drawn on the map, to efficiently update
    val (drawnLines, setDrawnLines) = remember { mutableStateOf(emptyMap<MapLine, Line>()) }
    val (drawnSymbols, setDrawnSymbols) = remember { mutableStateOf(emptyMap<MapSymbol, Symbol>()) }

    val updatedDirection by rememberUpdatedState(LocalLayoutDirection.current)
    val updatedDensity by rememberUpdatedState(LocalDensity.current)

    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapLibre.getInstance(context)
            MapView(context).apply {
                observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_CREATE -> onCreate(null)
                        Lifecycle.Event.ON_START -> onStart()
                        Lifecycle.Event.ON_RESUME -> onResume()
                        Lifecycle.Event.ON_PAUSE -> onPause()
                        Lifecycle.Event.ON_STOP -> onStop()
                        Lifecycle.Event.ON_DESTROY -> onDestroy()
                        else -> throw IllegalStateException()
                    }
                }
                getMapAsync { map ->
                    map.uiSettings.attributionGravity = Gravity.BOTTOM or Gravity.END
                    map.getStyle { style ->
                        lineManager = LineManager(this, map, style)
                        symbolManager = SymbolManager(this, map, style)
                    }
                }
            }
        },
        update = { mapView ->
            mapView.getMapAsync { map -> map.setStyle(updatedStyleUrl) }
            mapView.applyUiSettings(updatedUiSettings, updatedDensity, updatedDirection)
            lineManager?.applyLines(drawnLines, updatedLines, setDrawnLines)
            symbolManager?.applySymbols(drawnSymbols, updatedSymbols, setDrawnSymbols)
        }
    )

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, observer) {
        observer?.let { lifecycle.addObserver(it) }
        onDispose { observer?.let { lifecycle.removeObserver(it) } }
    }
}

fun MapView.applyUiSettings(
    uiSettings: MapUiSettings,
    density: Density,
    dir: LayoutDirection
) {
    getMapAsync { map ->
        uiSettings.apply {
            map.uiSettings.isLogoEnabled = isLogoEnabled
            map.uiSettings.isAttributionEnabled = isAttributionEnabled
            map.uiSettings.isCompassEnabled = isCompassEnabled

            map.uiSettings.isTiltGesturesEnabled = isTiltGesturesEnabled
            map.uiSettings.isZoomGesturesEnabled = isZoomGesturesEnabled
            map.uiSettings.isRotateGesturesEnabled = isRotateGesturesEnabled
            map.uiSettings.isScrollGesturesEnabled = isScrollGesturesEnabled

            with(density) {
                val leftUiPadding =
                    padding.calculateLeftPadding(dir).toPx().roundToInt()
                val topUiPadding =
                    padding.calculateTopPadding().toPx().roundToInt()
                val rightUiPadding =
                    padding.calculateRightPadding(dir).toPx().roundToInt()
                val bottomUiPadding =
                    padding.calculateBottomPadding().toPx().roundToInt()

                map.uiSettings.setAttributionMargins(
                    leftUiPadding,
                    topUiPadding,
                    rightUiPadding,
                    bottomUiPadding
                )
                map.uiSettings.setLogoMargins(
                    leftUiPadding,
                    topUiPadding,
                    rightUiPadding,
                    bottomUiPadding
                )
                map.uiSettings.setCompassMargins(
                    leftUiPadding,
                    topUiPadding,
                    rightUiPadding,
                    bottomUiPadding
                )
            }
        }
    }
}

fun LineManager.applyLines(
    beforeLines: Map<MapLine, Line>,
    afterLines: Set<MapLine>,
    callback: (afterLines: Map<MapLine, Line>) -> Unit
) {
    val linesToRemove = beforeLines.keys - afterLines
    val linesToAdd = afterLines - beforeLines.keys

    val result = beforeLines.toMutableMap()

    for (line in linesToRemove) {
        delete(beforeLines.getValue(line))
        result.remove(line)
    }

    for (line in linesToAdd) {
        val newLine = create(
            LineOptions()
                .withLatLngs(line.points.map { LatLng(it.lat, it.lon) })
                .withLineWidth(2.0f)
                .withLineColor("#ff0000")
        )
        result[line] = newLine
    }

    callback(result)
}

fun SymbolManager.applySymbols(
    beforeSymbols: Map<MapSymbol, Symbol>,
    afterSymbols: Set<MapSymbol>,
    callback: (afterSymbols: Map<MapSymbol, Symbol>) -> Unit
) {
    val symbolsToRemove = beforeSymbols.keys - afterSymbols
    val symbolsToAdd = afterSymbols - beforeSymbols.keys

    val result = beforeSymbols.toMutableMap()

    for (symbol in symbolsToRemove) {
        delete(beforeSymbols.getValue(symbol))
        result.remove(symbol)
    }

    for (symbol in symbolsToAdd) {
        val newSymbol = create(
            SymbolOptions()
                .withTextColor("#ff0000")
                .withTextField(symbol.text)
                .withTextFont(arrayOf("Noto Sans Regular"))
                .withLatLng(LatLng(symbol.point.lat, symbol.point.lon))
        )
        result[symbol] = newSymbol
    }

    callback(result)
}
