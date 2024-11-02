package dev.sargunv.traintracker

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class IosDatabaseDriverFactory : DatabaseDriverFactory {
  override fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver {
    return NativeSqliteDriver(schema, name)
  }
}

@Suppress("unused") // called in Swift
fun initKoin() {
  startKoin {
    modules(commonModules + module { singleOf<DatabaseDriverFactory>(::IosDatabaseDriverFactory) })
  }
}
