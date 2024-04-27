package routes.api

import db.DatabaseSingleton.statement
import User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.usersRouting() {
    route("/users") {
        get {
            if (call.request.cookies["role"] != "admin") return@get call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            var filter: String
            val parameters = call.request.queryParameters
            if (!parameters.isEmpty()) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (parameters["role"] != null)
                    filterConditions.add("role = '" + parameters["role"] + "'")
                if (parameters["full_name"] != null)
                    filterConditions.add("full_name = '" + parameters["full_name"] + "'")
                if (parameters["date_of_birth"] != null)
                    filterConditions.add("date_of_birth = '" + parameters["date_of_birth"] + "'")
                if (parameters["phone_number"] != null)
                    filterConditions.add("phone_number = '" + parameters["phone_number"] + "'")
                if (parameters["email"] != null)
                    filterConditions.add("email = '" + parameters["email"] + "'")
                if (parameters["card_num"] != null)
                    filterConditions.add("card_num = '" + parameters["card_num"] + "'")
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"HumanResources\".\"Users\"$filter" +
                            " ORDER BY id_user ASC"
                )
            val users = ArrayList<User>()
            while (resultSet.next())
                users.add(
                    User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                    )
                )
            call.respondText(Json.encodeToString(users))
        }
        get("{id}") {
            if (call.request.cookies["role"] != "admin") return@get call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"HumanResources\".\"Users\"" +
                            " WHERE id_user = '$id'"
                )
            val users = ArrayList<User>()
            while (resultSet.next())
                users.add(
                    User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                    )
                )
            call.respondText(Json.encodeToString(users))
        }
        post("/insert") {
            if (call.request.cookies["role"] != "admin") return@post call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val parameters = call.receiveParameters()
            val role = parameters["role"] ?: return@post call.respondText(
                "Missing or malformed role",
                status = HttpStatusCode.BadRequest
            )
            val fullName = parameters["full_name"] ?: return@post call.respondText(
                "Missing or malformed full_name",
                status = HttpStatusCode.BadRequest
            )
            val dateOfBirth = parameters["date_of_birth"] ?: return@post call.respondText(
                "Missing or malformed date_of_birth",
                status = HttpStatusCode.BadRequest
            )
            val phoneNumber = parameters["phone_number"] ?: return@post call.respondText(
                "Missing or malformed phone_number",
                status = HttpStatusCode.BadRequest
            )
            val email = parameters["email"] ?: return@post call.respondText(
                "Missing or malformed email",
                status = HttpStatusCode.BadRequest
            )
            val cardNum = parameters["card_num"] ?: return@post call.respondText(
                "Missing or malformed card_num",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"HumanResources\".\"Users\" (role, full_name, date_of_birth, phone_number, email, card_num)" +
                        " VALUES ('$role', '$fullName', '$dateOfBirth', '$phoneNumber', '$email', '$cardNum')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            if (call.request.cookies["role"] != "admin") return@post call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            var setExpression: String
            val parameters = call.receiveParameters()
            if (!parameters.isEmpty()) {
                setExpression = " SET "
                val setParameters = ArrayList<String>()
                if (parameters["role"] != null)
                    setParameters.add("role = '" + parameters["role"] + "'")
                if (parameters["full_name"] != null)
                    setParameters.add("full_name = '" + parameters["full_name"] + "'")
                if (parameters["date_of_birth"] != null)
                    setParameters.add("date_of_birth = '" + parameters["date_of_birth"] + "'")
                if (parameters["phone_number"] != null)
                    setParameters.add("phone_number = '" + parameters["phone_number"] + "'")
                if (parameters["email"] != null)
                    setParameters.add("email = '" + parameters["email"] + "'")
                if (parameters["card_num"] != null)
                    setParameters.add("card_num = '" + parameters["card_num"] + "'")
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
            if (call.request.cookies["role"] != "admin") return@delete call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
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
