import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import routes.*
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
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

        registerStoragesRoutes()
        registerUsersRoutes()
        registerBankHistoryRoutes()
        registerItemLocationRoutes()
        registerItemsRoutes()
        registerRentsRoutes()

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
