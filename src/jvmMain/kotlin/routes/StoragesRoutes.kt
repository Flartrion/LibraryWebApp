package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.routing.*

fun Route.storagesRouting() {
    route("/storages") {
        get("{id}") {
            val temp = statement
                .executeQuery(
                    "SELECT * FROM \"Facilities\".\"Storages\"" +
                            " WHERE address = 'где-то'"
                )
        }
        post("/insert/{id}") {
            val temp = statement
                .executeQuery(
                    "INSERT INTO \"Facilities\".\"Storages\" (address)" +
                            " VALUES ('проспект Джорджа Джостара, д. 17')"
                )
        }
        post("/update/{id}") {
            val temp = statement
                .executeQuery(
                    "UPDATE \"Facilities\".\"Storages\"" +
                            " SET address = 'где-то'" +
                            " WHERE id_storage = 'id'"
                )
        }
        delete("/delete/{id}") {
            statement.executeUpdate(
                "DELETE FROM \"Facilities\".\"Storages\"" +
                        " WHERE id_storage = 'id'"
            )
        }
    }
}

fun Application.registerStoragesRoutes() {
    routing {
        storagesRouting()
    }
}