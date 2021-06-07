package routes

import DataBase.statement
import Rents
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.rentsRouting() {
    route("/rents") {
        get {
            var filter: String
            val parameters = call.receiveParameters()
            if (!parameters.isEmpty()) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (parameters["id_user"] != null)
                    filterConditions.add("id_user = '" + parameters["id_user"] + "'")
                if (parameters["id_item"] != null)
                    filterConditions.add("id_item = '" + parameters["id_item"] + "'")
                if (parameters["from_date"] != null)
                    filterConditions.add("from_date = '" + parameters["from_date"] + "'")
                if (parameters["until_date"] != null)
                    filterConditions.add("until_date = '" + parameters["until_date"] + "'")
                if (parameters["id_storage"] != null)
                    filterConditions.add("id_storage = '" + parameters["id_storage"] + "'")
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Rents\"$filter"
                )
            val rents = ArrayList<Rents>()
            while (resultSet.next())
                rents.add(
                    Rents(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                    )
                )
            call.respondText(Json.encodeToString(rents))
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Rents\"" +
                            " WHERE id_rent = '$id'"
                )
            val rents = ArrayList<Rents>()
            while (resultSet.next())
                rents.add(
                    Rents(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                    )
                )
            call.respondText(Json.encodeToString(rents))
        }
        post("/insert") {
            val parameters = call.receiveParameters()
            val idUser = parameters["id_user"] ?: return@post call.respondText(
                "Missing or malformed id_user",
                status = HttpStatusCode.BadRequest
            )
            val idItem = parameters["id_item"] ?: return@post call.respondText(
                "Missing or malformed id_item",
                status = HttpStatusCode.BadRequest
            )
            val fromDate = parameters["from_date"] ?: return@post call.respondText(
                "Missing or malformed from_date",
                status = HttpStatusCode.BadRequest
            )
            val untilDate = parameters["until_date"] ?: return@post call.respondText(
                "Missing or malformed until_date",
                status = HttpStatusCode.BadRequest
            )
            val idStorage = parameters["id_storage"] ?: return@post call.respondText(
                "Missing or malformed id_storage",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Rents\" (id_user, id_item, from_date, until_date, id_storage)" +
                        " VALUES ('$idUser', '$idItem', '$fromDate', '$untilDate', '$idStorage')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            var setExpression: String
            val parameters = call.receiveParameters()
            if (!parameters.isEmpty()) {
                setExpression = " SET "
                val setParameters = ArrayList<String>()
                if (parameters["id_user"] != null)
                    setParameters.add("id_user = '" + parameters["id_user"] + "'")
                if (parameters["id_item"] != null)
                    setParameters.add("id_item = '" + parameters["id_item"] + "'")
                if (parameters["from_date"] != null)
                    setParameters.add("from_date = '" + parameters["from_date"] + "'")
                if (parameters["until_date"] != null)
                    setParameters.add("until_date = '" + parameters["until_date"] + "'")
                if (parameters["id_storage"] != null)
                    setParameters.add("id_storage = '" + parameters["id_storage"] + "'")
                setExpression += setParameters[0]
                setParameters.removeAt(0)
                for (i in setParameters) {
                    setExpression += ", $i"
                }
            } else
                return@post call.respondText("Missing or malformed parameters", status = HttpStatusCode.BadRequest)
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"Rents\"" +
                        setExpression +
                        " WHERE id_rent = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Rents\"" +
                        " WHERE id_rent = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerRentsRoutes() {
    routing {
        rentsRouting()
    }
}