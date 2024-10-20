package dev.sargunv.traintracker

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class Platform : IPlatform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override fun createDriver(
        schema: SqlSchema<QueryResult.AsyncValue<Unit>>,
        name: String
    ): SqlDriver {
        return AndroidSqliteDriver(schema.synchronous(), context, name)
    }
}

fun initKoin(context: Context) {
    val platformModule = module {
        singleOf<IPlatform>(::Platform)
        singleOf<DatabaseDriverFactory>({ AndroidDatabaseDriverFactory(context) })
    }
    startKoin {
        androidLogger()
        androidContext(context)
        modules(listOf(appModule, platformModule))
    }
}