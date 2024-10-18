package dev.sargunv.traintracker

import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun initKoin(platform: () -> IPlatform) {
    val platformModule = module {
        singleOf<IPlatform>(platform)
    }

    startKoin {
        modules(listOf(platformModule))
    }
}