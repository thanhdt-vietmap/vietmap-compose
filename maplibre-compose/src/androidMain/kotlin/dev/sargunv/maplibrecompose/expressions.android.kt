package dev.sargunv.maplibrecompose

import org.maplibre.android.style.expressions.Expression
import org.maplibre.android.style.expressions.Expression.*

internal fun fancyWidth(): Expression? {
  return interpolate(exponential(2), zoom(), stop(0, 1), stop(10, 250))
}
