package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.copiesRouting() {
    route("/copies") {
        get("{id}") {
            val id = call.receiveParameters()["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Copies\"" +
                            " WHERE id_copy = '$id'"
                )
        }
        post("/insert") {
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Copies\" (id_item, tome, language, bank)" +
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
                "UPDATE \"Inventory\".\"Copies\"" +
                        " SET id_item = '', tome = '', language = '', bank = ''" +
                        " WHERE id_copy = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.receiveParameters()["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Copies\"" +
                        " WHERE id_copy = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerCopiesRoutes() {
    routing {
        copiesRouting()
    }
}