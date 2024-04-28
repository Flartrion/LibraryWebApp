package routes

import dataType.BankHistoryEntry
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.bankHistoryRouting() {
    route("/bankHistory") {
        TODO("Pending redesign. Careful here, since it's 2 entities that get updated on creation and deletion")
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
                if (parameters["id_item"] != null)
                    filterConditions.add("id_item = '" + parameters["id_item"] + "'")
                if (parameters["change"] != null)
                    filterConditions.add("change = '" + parameters["change"] + "'")
                if (parameters["date"] != null)
                    filterConditions.add("date = '" + parameters["date"] + "'")
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
//                    "SELECT * FROM \"Inventory\".\"BankHistory\"$filter" +
//                            " ORDER BY date DESC"
//                )
            val bankHistoryEntry = ArrayList<BankHistoryEntry>()
//            while (resultSet.next())
//                bankHistoryEntry.add(
//                    BankHistoryEntry(
//                        resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5)
//                    )
//                )
            call.respondText(Json.encodeToString(bankHistoryEntry))
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
//                    "SELECT * FROM \"Inventory\".\"BankHistory\"" +
//                            " WHERE id_entry = '$id'"
//                )
            val bankHistoryEntry = ArrayList<BankHistoryEntry>()
//            while (resultSet.next())
//                bankHistoryEntry.add(
//                    BankHistoryEntry(
//                        resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5)
//                    )
//                )
            call.respondText(Json.encodeToString(bankHistoryEntry))
        }
        post("/insert") {
            if (call.request.cookies["role"] != "admin") return@post call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val parameters = call.receiveParameters()
            val idItem = parameters["id_item"] ?: return@post call.respondText(
                "Missing or malformed id_item",
                status = HttpStatusCode.BadRequest
            )
            val change = parameters["change"] ?: return@post call.respondText(
                "Missing or malformed change",
                status = HttpStatusCode.BadRequest
            )
            val date = parameters["date"] ?: return@post call.respondText(
                "Missing or malformed date",
                status = HttpStatusCode.BadRequest
            )
            val idStorage = parameters["id_storage"] ?: return@post call.respondText(
                "Missing or malformed id_storage",
                status = HttpStatusCode.BadRequest
            )

//            statement.executeUpdate(
//                "INSERT INTO \"Inventory\".\"BankHistory\" (id_item, change, date, id_storage)" +
//                        " VALUES ('$idItem', '$change', '$date', '$idStorage')"
//            )
//            if (
//                statement.executeUpdate(
//                    "UPDATE \"Inventory\".\"dataType.ItemLocation\"" +
//                            " SET amount = amount + $change" +
//                            " WHERE id_storage = '$idStorage' AND id_item = '$idItem'"
//                ) == 0
//            ) {
//                statement.executeUpdate(
//                    "INSERT INTO \"Inventory\".\"dataType.ItemLocation\"" +
//                            " (id_item, id_storage, amount) VALUES" +
//                            " ('$idItem', '$idStorage', '$change')"
//                )
//            }
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
                if (parameters["id_item"] != null)
                    setParameters.add("id_item = '" + parameters["id_item"] + "'")
                if (parameters["change"] != null)
                    setParameters.add("change = '" + parameters["change"] + "'")
                if (parameters["date"] != null)
                    setParameters.add("date = '" + parameters["date"] + "'")
                if (parameters["id_storage"] != null)
                    setParameters.add("id_storage = '" + parameters["id_storage"] + "'")
                setExpression += setParameters[0]
                setParameters.removeAt(0)
                for (i in setParameters) {
                    setExpression += ", $i"
                }
            } else
                return@post call.respondText("Missing or malformed parameters", status = HttpStatusCode.BadRequest)
//            statement.executeUpdate(
//                "UPDATE \"Inventory\".\"BankHistory\"" +
//                        setExpression +
//                        " WHERE id_entry = '$id'"
//            )
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
//            val data = statement.executeQuery(
//                "SELECT * FROM \"Inventory\".\"BankHistory\"" +
//                        " WHERE id_entry = '$id'"
//            )
//            data.next()
//            val entry = BankHistoryEntry(
//                data.getString(1),
//                data.getString(2),
//                data.getString(3),
//                data.getString(4),
//                data.getString(5)
//            )
//            try {
//                statement.executeUpdate(
//                    "UPDATE \"Inventory\".\"dataType.ItemLocation\"" +
//                            " SET amount = amount - ${entry.change}" +
//                            " WHERE id_storage = ${entry.id_storage} AND id_item = ${entry.id_item}"
//                )
//                statement.executeUpdate(
//                    "DELETE FROM \"Inventory\".\"BankHistory\"" +
//                            " WHERE id_entry = '$id'"
//                )
//                call.respond(HttpStatusCode.OK)
//            } catch (e: Exception) {
//                call.respond(HttpStatusCode.BadRequest, e.message ?: "Unknown error")
//            }
        }
    }
}

