package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.routing.*

fun Route.itemsRouting() {
    route("/items") {
        get("{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Items\"" +
                            " WHERE isbn = 'id'"
                )
        }
        post("/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"Items\" (isbn, rlbc, title, authors, type, details)" +
                            " VALUES ('', '', '', '', '', '')"
                )
        }
        post("/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"Items\"" +
                            "SET isbn = '', rlbc = '', title = '', authors = '', type = '', details = ''" +
                            " WHERE id_item = 'id'"
                )
        }
        delete("/delete/{id}") {
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Items\"" +
                        " WHERE id_item = 'id'"
            )
        }
    }
}

fun Application.registerItemsRoutes() {
    routing {
        itemsRouting()
    }
}