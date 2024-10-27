package dev.sargunv.traintracker

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.sargunv.traintracker.gtfs.gtfsModule
import dev.sargunv.traintracker.ui.TrainMapViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { TrainMapViewModel(gtfsSdk = get()) }
}

val commonModules = listOf(gtfsModule, appModule)

interface DatabaseDriverFactory {
    fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver
}