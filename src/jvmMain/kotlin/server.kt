import DataBase.statement
import com.ibm.java.diagnostics.utils.Context.logger
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.html.*
import routes.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        anyHost()
    }
    install(Compression) {
        gzip()
    }
    install(Sessions) {
        cookie<UserSession>("ktor_session_cookie", storage = SessionStorageMemory())
    }
    install(Authentication) {
        session<UserSession>("authSession") {
            validate { session: UserSession ->
                logger.info { "User ${session.name} logged in by existing session" }
                session
            }
            challenge {
                logger.info { "No valid session found for this route, redirecting to login form" }
//                call.sessions.set()
                call.respondRedirect("/login")
            }
        }
    }

    routing {

        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }

        get("/login") {

        }

        post("/login") {
//            val parameters = call.receiveParameters()
//            val username = parameters["username"] ?: return@post call.respondText(
//                "Missing or malformed role",
//                status = HttpStatusCode.BadRequest
//            )
//            val password = parameters["password"] ?: return@post call.respondText(
//                "Missing or malformed role",
//                status = HttpStatusCode.BadRequest
//            )
//            val resultSet = statement
//                .executeQuery(
//                    "SELECT role, email, card_num FROM \"HumanResources\".\"Users\"" +
//                            "WHERE email = '$username'"
//                )
//            val role =
            if (true) {
                // Store principal in session
                call.sessions.set(UserSession("Bog Boss", "admin"))
                call.respondRedirect("/")
            } else {
                // Stay on login page
                call.respondRedirect("/login")
            }
        }

        authenticate {
            registerStoragesRoutes()
            registerUsersRoutes()
            registerBankHistoryRoutes()
            registerItemLocationRoutes()
            registerItemsRoutes()
            registerRentsRoutes()
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

data class UserSession(
    val name: String,
    val roles: String
) : Principal