import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*
import java.sql.*
import java.util.*


fun HTML.index() {
    head {
        title("Et verbis virtus")
    }
    body {
        id = "root"
        script(src = "/static/js.js") {}
    }

}

fun main() {
    val url = "jdbc:postgresql://localhost:5432/library"
    val props = Properties()
    props.setProperty("user", "postgres")
    props.setProperty("password", "volume")
//    props.setProperty("ssl", "true")

//    val conn = DriverManager.getConnection(url, props)

    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            get("/goodplace") {
                call.respondHtml {
                    head {
                        title("ROOTS, BLOODY ROOTS")
                    }
                    body {
                        id = "root"
                        script(src = "/static/js.js") {}
                    }
                }
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
            post("/auth") {
                val params = call.receiveParameters()
                call.respond(HttpStatusCode.NotImplemented, "Wrong door, leatherman \n${params["login"]}, ${params["pass"]}")
            }
            post("/post") {
                call.respond("Okay")
            }
            static("/static") {
                resources()
            }
        }
    }.start(wait = true)
}