package db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import db.entity.UserEntity
import db.model.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Schema
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
        var url = config.property("database.jdbcURL").getString() + ':' + config.property("database.hostname")
            .getString() + ':' + config.property("database.port").getString() + '/'


        val librarySchema = Schema("library")

        url += "ConanTheLibrarian"
        val database = Database.connect(
            createHikariDataSource(
                url,
                driver,
                config.property("database.username").getString(),
                config.property("database.password").getString()
            )
        )
        transaction(database) {
            if (config.property("database.init").getString().toBoolean()) {
                SchemaUtils.createSchema(librarySchema)
                SchemaUtils.setSchema(librarySchema)
                SchemaUtils.create(Items)
                SchemaUtils.create(Storages)
                SchemaUtils.create(ItemLocations)
                SchemaUtils.create(BankHistory)
                SchemaUtils.create(Users)
                SchemaUtils.create(Rents)
                UserEntity.new {
                    fullName = "admin"
                    email = "admin"
                    phoneNumber = "admin"
                    dob = LocalDate(0, 1, 1)
                    role = 1
                }
            } else SchemaUtils.setSchema(librarySchema)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

}