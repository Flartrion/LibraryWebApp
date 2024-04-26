import DataBase.statement
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import routes.*
import java.sql.SQLException


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
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
                TODO("This is baaaaaad")
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

        storagesRouting()
        itemLocationRouting()
        bankHistoryRouting()
        itemLocationRouting()
        itemsRouting()
        rentsRouting()

        staticResources("/resources", basePackage = "") {
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