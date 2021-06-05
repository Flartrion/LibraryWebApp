import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.html.*
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val url = "jdbc:postgresql://localhost:5432/library"
    val properties = Properties()
    properties.setProperty("user", "postgres")
    properties.setProperty("password", "1")

    val connection = connect(url, properties)!!
    val statement = connection.createStatement()

//    install(ContentNegotiation) {
//        json()
//    }

    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        anyHost()
    }

//    install(Compression) {
//        gzip()
//    }

    routing {
        get("/hello") {
            call.respondText("Hello, API!")
        }
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }
        get("/book/{id}") {
            val bookId = call.parameters["id"]
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title("urbook")
                }
                body {
                    id = "root"
                    b {
                        +(bookId ?: "Fuck you")
                    }
                }
            }
        }
        post("/storages") {
            call.respond("yep")
        }
        post("/search") {
            val params = call.receiveParameters()
            val name = params["name"]
            val authors = params["authors"]
            val type = params["type"]
            val sort = params["sorting"]
            val ascDesc = params["ascDesc"]
            println("$name + $authors + $type + $sort + $ascDesc")
            call.respondText("$name + $authors + $type + $sort + $ascDesc")
        }
        post("/") {
            call.respond("Ok")
        }

        //------------------------------------------------------------------------------------------------Authentication
        post("/auth") {
            val params = call.receiveParameters()
            call.respond(
                HttpStatusCode.NotImplemented,
                "Wrong door, leatherman \n${params["login"]}, ${params["pass"]}"
            )
        }

        //------------------------------------------------------------------------------------------------------Storages
        get("/storages/{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Facilities\".\"Storages\"" +
                            " WHERE address = 'где-то'"
                )
        }
        post("/storages/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Facilities\".\"Storages\" (address)" +
                            " VALUES ('проспект Джорджа Джостара, д. 17')"
                )
        }
        post("/storages/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Facilities\".\"Storages\"" +
                            " SET address = 'где-то'" +
                            " WHERE id_storage = 'id'"
                )
        }
        delete("/storages/delete/{id}") {
            statement.executeQuery(
                "DELETE FROM \"Facilities\".\"Storages\"" +
                        " WHERE id_storage = 'id'"
            )
        }
        //---------------------------------------------------------------------------------------------------------Users
        get("/users/{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"HumanResources\".\"Users\"" +
                            " WHERE email = 'что-то'"
                )
        }
        post("/users/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"HumanResources\".\"Users\" (role, full_name, date_of_birth, phone_number, email)" +
                            " VALUES ('','','','','')"
                )
        }
        post("/users/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"HumanResources\".\"Users\"" +
                            " SET role = '', full_name = '', date_of_birth = '', phone_number = '', email = ''" +
                            " WHERE id_user = 'id'"
                )
        }
        delete("/users/delete/{id}") {
            statement.executeQuery(
                "DELETE FROM \"HumanResources\".\"Users\"" +
                        " WHERE id_user = 'id'"
            )
        }
        //---------------------------------------------------------------------------------------------------BankHistory
        get("/bankHistory/{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"BankHistory\"" +
                            " WHERE id_copy = 'что-то'"
                )
        }
        post("/bankHistory/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"BankHistory\" (id_copy, change, date)" +
                            " VALUES ('', '', '')"
                )
        }
        post("/bankHistory/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"BankHistory\"" +
                            " SET id_copy = '', change = '', date = ''" +
                            " WHERE id_entry = 'id'"
                )
        }
        delete("/bankHistory/delete/{id}") {
            statement.executeQuery(
                "DELETE FROM \"Inventory\".\"BankHistory\"" +
                        " WHERE id_entry = 'id'"
            )
        }
        //--------------------------------------------------------------------------------------------------------Copies
        get("/copies/{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Copies\"" +
                            " WHERE id_item = 'id'"
                )
        }
        post("/copies/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"Copies\" (id_item, tome, language, bank)" +
                            " VALUES ('', '', '', '')"
                )
        }
        post("/copies/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"Copies\"" +
                            " SET id_item = '', tome = '', language = '', bank = ''" +
                            " WHERE id_copy = 'id'"
                )
        }
        delete("/copies/delete/{id}") {
            statement.executeQuery(
                "DELETE FROM \"Inventory\".\"Copies\"" +
                        " WHERE id_copy = ''"
            )
        }
        //--------------------------------------------------------------------------------------------------CopyLocation
        get("/copyLocation/{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"CopyLocation\"" +
                            " WHERE id_copy = 'id'"
                )
        }
        post("/copyLocation/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"CopyLocation\" (id_copy, id_storage, amount)" +
                            " VALUES ('', '', '')"
                )
        }
        post("/copyLocation/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"CopyLocation\"" +
                            " SET id_copy = '', id_storage = '', amount = ''" +
                            " WHERE id_copy = 'id'"
                )
        }
        delete("/copyLocation/delete/{id}") {
            statement.executeQuery(
                "DELETE FROM \"Inventory\".\"CopyLocation\"" +
                        " WHERE id_copy = 'id'"
            )
        }
        //---------------------------------------------------------------------------------------------------------Items
        get("/items/{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Items\"" +
                            " WHERE isbn = 'id'"
                )
        }
        post("/items/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"Items\" (isbn, rlbc, title, authors, type, details)" +
                            " VALUES ('', '', '', '', '', '')"
                )
        }
        post("/items/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"Items\"" +
                            "SET isbn = '', rlbc = '', title = '', authors = '', type = '', details = ''" +
                            " WHERE id_item = 'id'"
                )
        }
        delete("/items/delete/{id}") {
            statement.executeQuery(
                "DELETE FROM \"Inventory\".\"Items\"" +
                        " WHERE id_item = 'id'"
            )
        }
        //---------------------------------------------------------------------------------------------------------Rents
        get("/rents/{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Rents\"" +
                            " WHERE id_user = '' AND id_copy = ''"
                )
        }
        post("/rents/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"Rents\" (id_user, id_copy, from_date, until_date)" +
                            " VALUES ('', '', '', '')"
                )
        }
        post("rents/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"Rents\"" +
                            " SET id_user = '', id_copy = '', from_date = '', until_date = ''" +
                            " WHERE id_rent = 'id'"
                )
        }
        delete("/rents/delete/{id}") {
            statement.executeQuery(
                "DELETE FROM \"Inventory\".\"Rents\"" +
                        " WHERE id_rent = 'id'"
            )
        }

        get("/image/{name}") {
            val picname = call.parameters["name"]
            val file = File("C:/resources/$picname")
            println(file.absolutePath)
            call.respondFile(file)
        }
        static("/static") {
            resources()
        }
    }
}

fun HTML.index() {
    head {
        title("In verbis virtus")
    }
    body {
        id = "root"
        script(src = "/static/js.js") {}
    }

}

fun connect(url: String, properties: Properties): Connection? {
    val connection: Connection
    try {
        connection =
            DriverManager.getConnection(url, properties.getProperty("user"), properties.getProperty("password"))
        println("Successfully connected to database")
    } catch (e: SQLException) {
        println(e.message)
        return null
    }

    return connection
}