package routes

import DataBase.statement
import Users
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.usersRouting() {
    route("/users") {
        get {
            var filter: String
            if (!call.receiveParameters().isEmpty()) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (call.receiveParameters()["role"] != null)
                    filterConditions.add("role = " + call.receiveParameters()["role"])
                if (call.receiveParameters()["full_name"] != null)
                    filterConditions.add("full_name = " + call.receiveParameters()["full_name"])
                if (call.receiveParameters()["date_of_birth"] != null)
                    filterConditions.add("date_of_birth = " + call.receiveParameters()["date_of_birth"])
                if (call.receiveParameters()["phone_number"] != null)
                    filterConditions.add("phone_number = " + call.receiveParameters()["phone_number"])
                if (call.receiveParameters()["email"] != null)
                    filterConditions.add("email = " + call.receiveParameters()["email"])
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"HumanResources\".\"Users\"$filter"
                )
            val users = ArrayList<Users>()
            while (resultSet.next())
                users.add(
                    Users(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                    )
                )
            call.respondText(Json.encodeToString(users))
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"HumanResources\".\"Users\"" +
                            " WHERE id_user = '$id'"
                )
            val users = ArrayList<Users>()
            while (resultSet.next())
                users.add(
                    Users(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                    )
                )
            call.respondText(Json.encodeToString(users))
        }
        post("/insert") {
            val role = call.receiveParameters()["role"] ?: return@post call.respondText(
                "Missing or malformed role",
                status = HttpStatusCode.BadRequest
            )
            val fullName = call.receiveParameters()["full_name"] ?: return@post call.respondText(
                "Missing or malformed full_name",
                status = HttpStatusCode.BadRequest
            )
            val dateOfBirth = call.receiveParameters()["date_of_birth"] ?: return@post call.respondText(
                "Missing or malformed date_of_birth",
                status = HttpStatusCode.BadRequest
            )
            val phoneNumber = call.receiveParameters()["phone_number"] ?: return@post call.respondText(
                "Missing or malformed phone_number",
                status = HttpStatusCode.BadRequest
            )
            val email = call.receiveParameters()["email"] ?: return@post call.respondText(
                "Missing or malformed email",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"HumanResources\".\"Users\" (role, full_name, date_of_birth, phone_number, email)" +
                        " VALUES ($role, $fullName, $dateOfBirth, $phoneNumber, $email)"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            var setExpression: String
            if (!call.receiveParameters().isEmpty()) {
                setExpression = " SET "
                val setParameters = ArrayList<String>()
                if (call.receiveParameters()["role"] != null)
                    setParameters.add("role = " + call.receiveParameters()["role"])
                if (call.receiveParameters()["full_name"] != null)
                    setParameters.add("full_name = " + call.receiveParameters()["full_name"])
                if (call.receiveParameters()["date_of_birth"] != null)
                    setParameters.add("date_of_birth = " + call.receiveParameters()["date_of_birth"])
                if (call.receiveParameters()["phone_number"] != null)
                    setParameters.add("phone_number = " + call.receiveParameters()["phone_number"])
                if (call.receiveParameters()["email"] != null)
                    setParameters.add("email = " + call.receiveParameters()["email"])
                setExpression += setParameters[0]
                setParameters.removeAt(0)
                for (i in setParameters) {
                    setExpression += ", $i"
                }
            } else
                return@post call.respondText("Missing or malformed parameters", status = HttpStatusCode.BadRequest)
            statement.executeUpdate(
                "UPDATE \"HumanResources\".\"Users\"" +
                        setExpression +
                        " WHERE id_user = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"HumanResources\".\"Users\"" +
                        " WHERE id_user = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerUsersRoutes() {
    routing {
        usersRouting()
    }
}
