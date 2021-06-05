package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.routing.*

fun Route.copyLocationRouting() {
    route("/copyLocation") {
        get("{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"CopyLocation\"" +
                            " WHERE id_copy = 'id'"
                )
        }
        post("/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"CopyLocation\" (id_copy, id_storage, amount)" +
                            " VALUES ('', '', '')"
                )
        }
        post("/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"CopyLocation\"" +
                            " SET id_copy = '', id_storage = '', amount = ''" +
                            " WHERE id_copy = 'id'"
                )
        }
        delete("/delete/{id}") {
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