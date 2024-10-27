package dev.sargunv.traintracker

import android.content.Context
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.sargunv.traintracker.zip.Unzipper
import dev.sargunv.traintracker.zip.UnzipperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override fun createDriver(
        schema: SqlSchema<QueryResult.Value<Unit>>,
        name: String
    ): SqlDriver {
        return AndroidSqliteDriver(schema, context, name)
    }
}

fun initKoin(context: Context) {
    startKoin {
        androidLogger()
        androidContext(context)
        modules(commonModules + module {
            singleOf<DatabaseDriverFactory>({ AndroidDatabaseDriverFactory(context) })
            singleOf<Unzipper>(::UnzipperImpl)
        })
    }
}