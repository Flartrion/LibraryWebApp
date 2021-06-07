import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.*
import kotlin.system.exitProcess

object DataBase {
    val statement: Statement

    private val connection: Connection

    private const val url = "jdbc:postgresql://localhost:5432/library"

    private val properties = Properties()

    init {
        properties.setProperty("user", "postgres")
        properties.setProperty("password", "1")
        try {
            connection =
                DriverManager.getConnection(url, properties.getProperty("user"), properties.getProperty("password"))
            statement = connection.createStatement()
            println("Successfully connected to database")
        } catch (e: SQLException) {
            println(e.message)
            exitProcess(1)
        }
    }
}