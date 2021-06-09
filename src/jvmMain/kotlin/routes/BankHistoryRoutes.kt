package routes

import BankHistory
import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.bankHistoryRouting() {
    route("/bankHistory") {
        get {
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
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"BankHistory\"$filter" +
                            " ORDER BY date DESC"
                )
            val bankHistory = ArrayList<BankHistory>()
            while (resultSet.next())
                bankHistory.add(
                    BankHistory(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                    )
                )
            call.respondText(Json.encodeToString(bankHistory))
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"BankHistory\"" +
                            " WHERE id_entry = '$id'"
                )
            val bankHistory = ArrayList<BankHistory>()
            while (resultSet.next())
                bankHistory.add(
                    BankHistory(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                    )
                )
            call.respondText(Json.encodeToString(bankHistory))
        }
        post("/insert") {
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
            try {
                statement.executeUpdate(
                    "INSERT INTO \"Inventory\".\"BankHistory\" (id_item, change, date, id_storage)" +
                            " VALUES ('$idItem', '$change', '$date', '$idStorage')"
                )
                call.respond(HttpStatusCode.OK)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Unknown error")
            }
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
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"BankHistory\"" +
                        setExpression +
                        " WHERE id_entry = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"BankHistory\"" +
                        " WHERE id_entry = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerBankHistoryRoutes() {
    routing {
        bankHistoryRouting()
    }
}

