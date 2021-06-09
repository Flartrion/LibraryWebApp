import DataBase.statement
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
        cookie<OriginalRequestURI>("original_request_cookie")
    }
    install(Authentication) {
        session<UserSession> {
            validate { session: UserSession ->
                session
            }
            challenge {
                call.sessions.set(OriginalRequestURI(call.request.uri))
                call.respondRedirect("/login")
            }
        }
    }

    routing {

        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }

        get("/login") {
            val parameters = call.receiveParameters()
            val username = parameters["username"] ?: return@get call.respondText(
                "Missing or malformed role",
                status = HttpStatusCode.BadRequest
            )
            val password = parameters["password"] ?: return@get call.respondText(
                "Missing or malformed role",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT role, email, card_num FROM \"HumanResources\".\"Users\"" +
                            "WHERE email = '$username' AND card_num = '$password'"
                )
            val role = resultSet.getString(1)
            call.respond(HttpStatusCode.OK, role)
        }

        authenticate {
            withRole("admin") {
                registerStoragesRoutes()
                registerUsersRoutes()
                registerBankHistoryRoutes()
                registerItemLocationRoutes()
                registerItemsRoutes()
                registerRentsRoutes()
            }
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

//data class UserSession(
//    val name: String,
//    val role: String
//) : Principal
data class UserSession(val name: String, val roles: Set<String> = emptySet()) : Principal
data class OriginalRequestURI(val uri: String)