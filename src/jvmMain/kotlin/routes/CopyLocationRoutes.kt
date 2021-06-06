package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.copyLocationRouting() {
    route("/copyLocation") {
        get("{id}") {
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"CopyLocation\"" +
                            " WHERE id_copy = 'id'"
                )
        }
        post("/insert") {
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"CopyLocation\" (id_copy, id_storage, amount)" +
                        " VALUES ('', '', '')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"CopyLocation\"" +
                        " SET id_copy = '', id_storage = '', amount = ''" +
                        " WHERE id_copy = 'id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"CopyLocation\"" +
                        " WHERE id_copy = 'id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerCopyLocationRoutes() {
    routing {
        copyLocationRouting()
    }
}