package vn.vietmap.vietmapjs

import org.w3c.dom.HTMLElement

internal fun <T : Any> jso(): T = js("({})") as T

internal inline fun <T : Any> jso(block: T.() -> Unit): T = jso<T>().apply(block)

public fun MapOptions(
  container: HTMLElement,
  disableAttributionControl: Boolean = false,
): MapOptions = jso {
  this.container = container
  if (disableAttributionControl) {
    this.attributionControl = false
  }
}

public fun LogoControlOptions(compact: Boolean? = null): LogoControlOptions = jso {
  compact?.let { this.compact = it }
}

public fun ScaleControlOptions(
  maxWidth: Double? = null,
  unit: String? = null,
): ScaleControlOptions = jso {
  maxWidth?.let { this.maxWidth = it }
  unit?.let { this.unit = it }
}

public fun AttributionControlOptions(
  compact: Boolean? = null,
  customAttribution: String? = null,
): AttributionControlOptions = jso {
  compact?.let { this.compact = it }
  customAttribution?.let { this.customAttribution = it }
}

public fun NavigationControlOptions(
  showCompass: Boolean? = null,
  showZoom: Boolean? = null,
  visualizePitch: Boolean? = null,
): NavigationControlOptions = jso {
  showCompass?.let { this.showCompass = it }
  showZoom?.let { this.showZoom = it }
  visualizePitch?.let { this.visualizePitch = it }
}

public fun JumpToOptions(
  center: LngLat? = null,
  zoom: Double? = null,
  bearing: Double? = null,
  pitch: Double? = null,
  padding: PaddingOptions? = null,
): JumpToOptions = jso {
  center?.let { this.center = it }
  zoom?.let { this.zoom = it }
  bearing?.let { this.bearing = it }
  pitch?.let { this.pitch = it }
  padding?.let { this.padding = it }
}

public fun PaddingOptions(
  top: Double? = null,
  bottom: Double? = null,
  left: Double? = null,
  right: Double? = null,
): PaddingOptions = jso {
  top?.let { this.top = it }
  bottom?.let { this.bottom = it }
  left?.let { this.left = it }
  right?.let { this.right = it }
}

public fun FitBoundsOptions(
  linear: Boolean? = null,
  maxZoom: Double? = null,
  offset: Point? = null,
  center: LngLat? = null,
  zoom: Double? = null,
  bearing: Double? = null,
  pitch: Double? = null,
  speed: Double? = null,
  curve: Double? = null,
  maxDuration: Double? = null,
  minZoom: Double? = null,
  padding: PaddingOptions? = null,
  screenSpeed: Double? = null,
): FitBoundsOptions = jso {
  linear?.let { this.linear = it }
  maxZoom?.let { this.maxZoom = it }
  offset?.let { this.offset = it }
  center?.let { this.center = it }
  zoom?.let { this.zoom = it }
  bearing?.let { this.bearing = it }
  pitch?.let { this.pitch = it }
  speed?.let { this.speed = it }
  curve?.let { this.curve = it }
  maxDuration?.let { this.maxDuration = it }
  minZoom?.let { this.minZoom = it }
  padding?.let { this.padding = it }
  screenSpeed?.let { this.screenSpeed = it }
}

public fun FlyToOptions(
  center: LngLat? = null,
  zoom: Double? = null,
  bearing: Double? = null,
  pitch: Double? = null,
  speed: Double? = null,
  curve: Double? = null,
  maxDuration: Double? = null,
  minZoom: Double? = null,
  padding: PaddingOptions? = null,
  screenSpeed: Double? = null,
): FlyToOptions = jso {
  center?.let { this.center = it }
  zoom?.let { this.zoom = it }
  bearing?.let { this.bearing = it }
  pitch?.let { this.pitch = it }
  speed?.let { this.speed = it }
  curve?.let { this.curve = it }
  maxDuration?.let { this.maxDuration = it }
  minZoom?.let { this.minZoom = it }
  padding?.let { this.padding = it }
  screenSpeed?.let { this.screenSpeed = it }
}

public fun EaseToOptions(
  center: LngLat? = null,
  zoom: Double? = null,
  bearing: Double? = null,
  pitch: Double? = null,
  padding: PaddingOptions? = null,
  duration: Double? = null,
  easing: ((Double) -> Double)? = null,
): EaseToOptions = jso {
  center?.let { this.center = it }
  zoom?.let { this.zoom = it }
  bearing?.let { this.bearing = it }
  pitch?.let { this.pitch = it }
  padding?.let { this.padding = it }
  duration?.let { this.duration = it }
  easing?.let { this.easing = it }
}

public fun QueryRenderedFeaturesOptions(
  availableImages: Array<String>? = null,
  layers: Array<String>? = null,
  filter: Expression? = null,
  validate: Boolean? = null,
): QueryRenderedFeaturesOptions = jso {
  availableImages?.let { this.availableImages = it }
  layers?.let { this.layers = it }
  filter?.let { this.filter = it }
  validate?.let { this.validate = it }
}

public typealias Expression = Array<*>
