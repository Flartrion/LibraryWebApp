package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.rentsRouting() {
    route("/rents") {
        get("{id}") {
            val id = call.receiveParameters()["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Rents\"" +
                            " WHERE id_rent = '$id'"
                )
        }
        post("/insert") {
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Rents\" (id_user, id_copy, from_date, until_date)" +
                        " VALUES ('', '', '', '')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            val id = call.receiveParameters()["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"Rents\"" +
                        " SET id_user = '', id_copy = '', from_date = '', until_date = ''" +
                        " WHERE id_rent = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.receiveParameters()["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Rents\"" +
                        " WHERE id_rent = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerRentsRoutes() {
    routing {
        rentsRouting()
    }
}