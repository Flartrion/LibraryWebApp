import db.DatabaseSingleton
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import routes.api.items.itemRoutes
import routes.api.storages.storagesRouting
import routes.api.users.userRoutes
import routes.api.login.loginRouting
import security.configureSecurity


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    configureSecurity()

    DatabaseSingleton.init(config = environment.config)
    routing {
//        get("/") {
//            call.respondHtml(HttpStatusCode.OK, HTML::index)
//        }

        loginRouting(this@module.environment.config)

        storagesRouting()
        itemRoutes()
        userRoutes()

//        rentsRouting()
//        itemLocationRouting()
//        bankHistoryRouting()

        get("/favicon.ico") {
            val favicon = call.resolveResource("/favicon.ico", "static")
            if (favicon != null) {
                call.respond(favicon)
            }
        }

        staticResources("/", "static", index = "index.html") {

        }

    }
}

fun HTML.index() {
    head {
        title("In verbis virtus")
    }
    body {
        id = "root"
        script(src = "/static/index.js") {}
    }

}