package routes

import User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.SQLException

fun Route.loginRouting() {
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
            val users = User(
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
        } catch (e: Exception) {
            call.respondText("Internal error", status = HttpStatusCode.BadGateway)
            e.printStackTrace()
        }
    }
}