package dev.sargunv.traintracker

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.sargunv.traintracker.gtfs.gtfsModule
import org.koin.dsl.module

val appModule = module {
  // TODO viewModel { SomeViewModel(gtfsSdk = get(named(GtfsAgency.ViaRail))) }
}

val commonModules = listOf(gtfsModule, appModule)

interface DatabaseDriverFactory {
  fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver
}
