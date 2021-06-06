package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.bankHistoryRouting() {
    route("/bankHistory") {
        get("{id}") {
            val id = call.receiveParameters()["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"BankHistory\"" +
                            " WHERE id_entry = '$id'"
                )
        }
        post("/insert") {
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"BankHistory\" (id_copy, change, date)" +
                        " VALUES ('', '', '')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            val id = call.receiveParameters()["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"BankHistory\"" +
                        " SET id_copy = '', change = '', date = ''" +
                        " WHERE id_entry = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.receiveParameters()["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"BankHistory\"" +
                        " WHERE id_entry = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerBankHistoryRoutes() {
    routing {
        bankHistoryRouting()
    }
}

