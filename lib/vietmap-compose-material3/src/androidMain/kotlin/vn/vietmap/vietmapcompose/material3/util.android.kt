package vn.vietmap.vietmapcompose.material3

import android.icu.util.LocaleData
import android.icu.util.ULocale
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import vn.vietmap.vietmapcompose.material3.controls.ScaleBarMeasure

@Composable
internal actual fun systemDefaultPrimaryMeasure(): ScaleBarMeasure? {
  if (android.os.Build.VERSION.SDK_INT < 28) return null
  val locales = LocalContext.current.resources.configuration.locales
  if (locales.isEmpty) return null
  return when (LocaleData.getMeasurementSystem(ULocale.forLocale(locales[0]))) {
    LocaleData.MeasurementSystem.SI -> ScaleBarMeasure.Metric
    LocaleData.MeasurementSystem.US -> ScaleBarMeasure.FeetAndMiles
    LocaleData.MeasurementSystem.UK -> ScaleBarMeasure.YardsAndMiles
    else -> null
  }
}
