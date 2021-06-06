package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.routing.*

fun Route.bankHistoryRouting() {
    route("/bankHistory") {
        get("{id}") {
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"BankHistory\"" +
                            " WHERE id_copy = 'что-то'"
                )
        }
        post("/insert") {
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"BankHistory\" (id_copy, change, date)" +
                        " VALUES ('', '', '')"
            )
        }
        post("/update/{id}") {
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"BankHistory\"" +
                        " SET id_copy = '', change = '', date = ''" +
                        " WHERE id_entry = 'id'"
            )
        }
        delete("{id}") {
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"BankHistory\"" +
                        " WHERE id_entry = 'id'"
            )
        }
    }
}

fun Application.registerBankHistoryRoutes() {
    routing {
        bankHistoryRouting()
    }
}

