package routes

import DataBase.statement
import io.ktor.application.*
import io.ktor.routing.*


fun Route.usersRouting() {
    route("/users") {
        get("{id}") {
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"HumanResources\".\"Users\"" +
                            " WHERE email = 'что-то'"
                )
        }
        post("/insert") {
            statement.executeUpdate(
                "INSERT INTO \"HumanResources\".\"Users\" (role, full_name, date_of_birth, phone_number, email)" +
                        " VALUES ('','','','','')"
            )
        }
        post("/update/{id}") {
            statement.executeUpdate(
                "UPDATE \"HumanResources\".\"Users\"" +
                        " SET role = '', full_name = '', date_of_birth = '', phone_number = '', email = ''" +
                        " WHERE id_user = 'id'"
            )
        }
        delete("{id}") {
            statement.executeUpdate(
                "DELETE FROM \"HumanResources\".\"Users\"" +
                        " WHERE id_user = 'id'"
            )
        }
    }
}

fun Application.registerUsersRoutes() {
    routing {
        usersRouting()
    }
}
