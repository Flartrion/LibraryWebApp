import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    routing {
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
            call.respond(HttpStatusCode.NotImplemented, "Wrong door, leatherman \n${params["login"]}, ${params["pass"]}")
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