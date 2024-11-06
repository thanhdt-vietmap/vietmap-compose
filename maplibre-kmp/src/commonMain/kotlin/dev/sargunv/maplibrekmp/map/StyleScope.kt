package dev.sargunv.maplibrekmp.map

import dev.sargunv.maplibrekmp.style.ExpressionScope
import dev.sargunv.maplibrekmp.style.Layer

public sealed class StyleScope private constructor(internal val manager: StyleManager) :
  ExpressionScope {

  public abstract fun add(layer: Layer)

  public fun addAll(vararg layers: Layer): Unit = layers.forEach(::add)

  public class Default internal constructor(manager: StyleManager) : StyleScope(manager) {
    public override fun add(layer: Layer): Unit = manager.addLayer(layer)

    public fun above(id: String, block: Above.() -> Unit): Unit = Above(manager, id).block()

    public fun below(id: String, block: Below.() -> Unit): Unit = Below(manager, id).block()

    public fun at(index: Int, block: At.() -> Unit): Unit = At(manager, index).block()
  }

  public class Above internal constructor(manager: StyleManager, private val above: String) :
    StyleScope(manager) {
    public override fun add(layer: Layer): Unit = manager.addLayerAbove(above, layer)
  }

  public class Below internal constructor(manager: StyleManager, private val below: String) :
    StyleScope(manager) {
    public override fun add(layer: Layer): Unit = manager.addLayerBelow(below, layer)
  }

  public class At internal constructor(manager: StyleManager, private val index: Int) :
    StyleScope(manager) {
    public override fun add(layer: Layer): Unit = manager.addLayerAt(index, layer)
  }
}
