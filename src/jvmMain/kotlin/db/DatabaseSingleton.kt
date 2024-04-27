package db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import db.model.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    private fun createHikariDataSource(url: String, driver: String, user: String, pass: String) =
        HikariDataSource(HikariConfig().apply {
            driverClassName = driver
            jdbcUrl = url
            username = user
            password = pass
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        })

    fun init(config: ApplicationConfig) {
        val driver = config.property("database.driverName").getString()
        val url = config.property("database.jdbcURL").getString() + ':' +
                config.property("database.hostname").getString() + ':' +
                config.property("database.port").getString()

        val database =
            Database.connect(
                createHikariDataSource(
                    url,
                    driver,
                    config.property("database.username").getString(),
                    config.property("database.password").getString()
                )
            )
        transaction(database) {
            SchemaUtils.create(Items)
            SchemaUtils.create(Storages)
            SchemaUtils.create(ItemLocations)
            SchemaUtils.create(BankHistory)
            SchemaUtils.create(Users)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}