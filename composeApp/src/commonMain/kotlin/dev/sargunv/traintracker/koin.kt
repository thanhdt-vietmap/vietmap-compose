package dev.sargunv.traintracker

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.sargunv.traintracker.gtfs.db.GtfsScheduleDb
import dev.sargunv.traintracker.ui.TrainMapViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { GtfsScheduleDb(driverFactory = get()) }
    viewModel { TrainMapViewModel(gtfsScheduleDb = get()) }
}

interface DatabaseDriverFactory {
    fun createDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, name: String): SqlDriver
}

interface IPlatform {
    val name: String
}