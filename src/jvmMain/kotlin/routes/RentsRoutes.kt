package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.routing.*

fun Route.rentsRouting() {
    route("/rents") {
        get("{id}") {
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Rents\"" +
                            " WHERE id_user = '' AND id_copy = ''"
                )
        }
        post("/insert") {
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Rents\" (id_user, id_copy, from_date, until_date)" +
                        " VALUES ('', '', '', '')"
            )
        }
        post("/update/{id}") {
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"Rents\"" +
                        " SET id_user = '', id_copy = '', from_date = '', until_date = ''" +
                        " WHERE id_rent = 'id'"
            )
        }
        delete("{id}") {
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Rents\"" +
                        " WHERE id_rent = 'id'"
            )
        }
    }
}

fun Application.registerRentsRoutes() {
    routing {
        rentsRouting()
    }
}