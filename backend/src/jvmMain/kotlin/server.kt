import db.DatabaseSingleton
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.html.*
import routes.api.*
import routes.api.items.itemRoutes
import routes.api.storages.storagesRouting
import routes.api.users.userRoutes
import routes.bankHistoryRouting
import routes.itemLocationRouting
import routes.loginRouting
import routes.rentsRouting


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    DatabaseSingleton.init(config = this.environment.config)
    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }

        loginRouting()

        storagesRouting()
        itemRoutes()
        userRoutes()
//        rentsRouting()
//        itemLocationRouting()
//        bankHistoryRouting()

        staticResources("/static", basePackage = "") {
        }
    }
}

fun HTML.index() {
    head {
        title("In verbis virtus")
    }
    body {
        id = "root"
        script(src = "/static/user.js") {}
    }

}