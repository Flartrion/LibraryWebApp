package routes

import dataType.Rent
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.SQLException

fun Route.rentsRouting() {
    route("/rents") {
        TODO("Pending redesign. Careful - rent reduces amount in itemLocations")
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
//            val resultSet = statement
//                .executeQuery(
//                    "SELECT * FROM \"Inventory\".\"Rents\"$filter" +
//                            " ORDER BY from_date DESC," +
//                            " until_date DESC"
//                )
            val rents = ArrayList<Rent>()
//            while (resultSet.next())
//                rents.add(
//                    Rent(
//                        resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5),
//                        resultSet.getString(6)
//                    )
//                )
            call.respondText(Json.encodeToString(rents))
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
//            val resultSet = statement
//                .executeQuery(
//                    "SELECT * FROM \"Inventory\".\"Rents\"" +
//                            " WHERE id_rent = '$id'"
//                )
            val rents = ArrayList<Rent>()
//            while (resultSet.next())
//                rents.add(
//                    Rent(
//                        resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5),
//                        resultSet.getString(6)
//                    )
//                )
            call.respondText(Json.encodeToString(rents))
        }
        post("/insert") {
            if (call.request.cookies["role"] != "admin" && call.request.cookies["role"] != "user") return@post call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
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
            try {
//                statement.executeUpdate(
//                    "INSERT INTO \"Inventory\".\"Rents\" (id_user, id_item, from_date, until_date, id_storage)" +
//                            " VALUES ('$idUser', '$idItem', '$fromDate', '$untilDate', '$idStorage')"
//                )
//                statement.executeUpdate(
//                    "UPDATE \"Inventory\".\"dataType.ItemLocation\"" +
//                            " SET amount = amount - 1" +
//                            " WHERE id_item = '$idItem' AND id_storage = '$idStorage'"
//                )
                call.respond(HttpStatusCode.OK)
            } catch (e: SQLException) {
                call.respondText(e.message ?: "Unrecognized error", status = HttpStatusCode.BadRequest)
            }
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
            try {
//                val result = statement.executeQuery(
//                    "SELECT id_item, id_storage FROM \"Inventory\".\"Rents\"" +
//                            " WHERE id_rent = '$id'"
//                )
//                result.next()
//                statement.executeUpdate(
//                    "UPDATE \"Inventory\".\"dataType.ItemLocation\"" +
//                            " SET amount = amount + 1" +
//                            " WHERE id_item = '${result.getString(1)}' AND id_storage = '${result.getString(2)}'"
//                )
//                statement.executeUpdate(
//                    "DELETE FROM \"Inventory\".\"Rents\"" +
//                            " WHERE id_rent = '$id'"
//                )
                call.respond(HttpStatusCode.OK)
            } catch (e: SQLException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Unrecognized error")
            }
        }
    }
}

