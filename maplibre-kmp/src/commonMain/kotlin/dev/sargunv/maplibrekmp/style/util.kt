package dev.sargunv.maplibrekmp.style

import kotlin.random.Random

internal object IncrementingId {
  private var incrementingId = Random.nextInt() % 1000 + 1000

  fun next(suffix: String): String = "COMPOSE_GENERATED_${incrementingId++}_$suffix"
}
