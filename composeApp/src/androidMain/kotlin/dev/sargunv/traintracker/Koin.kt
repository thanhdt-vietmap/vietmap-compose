package dev.sargunv.traintracker

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class Platform : IPlatform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

fun initKoin(context: Context) {
    val platformModule = module {
        singleOf<IPlatform>(::Platform)
    }
    startKoin {
        androidLogger()
        androidContext(context)
        modules(listOf(platformModule))
    }
}