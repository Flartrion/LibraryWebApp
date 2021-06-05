package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.routing.*

fun Route.copiesRouting() {
    route("/copies") {
        get("{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Copies\"" +
                            " WHERE id_item = 'id'"
                )
        }
        post("/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"Copies\" (id_item, tome, language, bank)" +
                            " VALUES ('', '', '', '')"
                )
        }
        post("/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"Copies\"" +
                            " SET id_item = '', tome = '', language = '', bank = ''" +
                            " WHERE id_copy = 'id'"
                )
        }
        delete("/delete/{id}") {
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Copies\"" +
                        " WHERE id_copy = ''"
            )
        }
    }
}

fun Application.registerCopiesRoutes() {
    routing {
        copiesRouting()
    }
}