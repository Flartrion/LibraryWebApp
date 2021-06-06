package routes

import DataBase.statement
import io.ktor.application.*
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
        }
        post("/update/{id}") {
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"CopyLocation\"" +
                        " SET id_copy = '', id_storage = '', amount = ''" +
                        " WHERE id_copy = 'id'"
            )
        }
        delete("{id}") {
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"CopyLocation\"" +
                        " WHERE id_copy = 'id'"
            )
        }
    }
}

fun Application.registerCopyLocationRoutes() {
    routing {
        copyLocationRouting()
    }
}