package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.itemsRouting() {
    route("/items") {
        get("{id}") {
            val id = call.receiveParameters()["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Items\"" +
                            " WHERE id_item = '$id'"
                )
        }
        post("/insert") {
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Items\" (isbn, rlbc, title, authors, type, details)" +
                        " VALUES ('', '', '', '', '', '')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            val id = call.receiveParameters()["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"Items\"" +
                        "SET isbn = '', rlbc = '', title = '', authors = '', type = '', details = ''" +
                        " WHERE id_item = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.receiveParameters()["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Items\"" +
                        " WHERE id_item = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerItemsRoutes() {
    routing {
        itemsRouting()
    }
}