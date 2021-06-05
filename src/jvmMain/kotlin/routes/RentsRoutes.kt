package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.routing.*

fun Route.rentsRouting() {
    route("/rents") {
        get("{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Rents\"" +
                            " WHERE id_user = '' AND id_copy = ''"
                )
        }
        post("/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Inventory\".\"Rents\" (id_user, id_copy, from_date, until_date)" +
                            " VALUES ('', '', '', '')"
                )
        }
        post("/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Inventory\".\"Rents\"" +
                            " SET id_user = '', id_copy = '', from_date = '', until_date = ''" +
                            " WHERE id_rent = 'id'"
                )
        }
        delete("/delete/{id}") {
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