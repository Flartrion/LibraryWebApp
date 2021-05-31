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

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        anyHost()
    }

    install(Compression) {
        gzip()
    }

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
                }
            }
        }
        post("/") {
            call.respond("Ok")
        }
        post("/auth") {
            val params = call.receiveParameters()
            call.respond(
                HttpStatusCode.NotImplemented,
                "Wrong door, leatherman \n${params["login"]}, ${params["pass"]}"
            )
        }
        delete {

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
    val connection : Connection
    try {
        connection =
            DriverManager.getConnection(url, properties.getProperty("user"), properties.getProperty("password"))
        println("Successfully connected to database")
    }
    catch (e: SQLException) {
        println(e.message)
        return null
    }

    return connection
}