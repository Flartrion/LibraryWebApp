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
import routes.loginRouting


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
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
        itemLocationRouting()
        bankHistoryRouting()
        itemLocationRouting()
        itemRoutes()
        rentsRouting()

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