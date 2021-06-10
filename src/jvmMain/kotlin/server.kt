import DataBase.statement
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import routes.*
import java.sql.SQLException

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        anyHost()
    }
    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }

        post("/login") {
            val parameters = call.receiveParameters()
            val username = parameters["username"] ?: return@post call.respondText(
                "Missing or malformed role",
                status = HttpStatusCode.BadRequest
            )
            val password = parameters["password"] ?: return@post call.respondText(
                "Missing or malformed role",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"HumanResources\".\"Users\"" +
                            "WHERE email = '$username' AND card_num = '$password'"
                )
            try {
                resultSet.next()
                val users = Users(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
                )
                call.respondText(Json.encodeToString(users))
            } catch (e: SQLException) {
                call.respondText("Login failed!", status = HttpStatusCode.BadRequest)
            }
        }

        registerStoragesRoutes()
        registerUsersRoutes()
        registerBankHistoryRoutes()
        registerItemLocationRoutes()
        registerItemsRoutes()
        registerRentsRoutes()

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