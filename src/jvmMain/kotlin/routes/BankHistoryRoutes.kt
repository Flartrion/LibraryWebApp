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
            if (!call.receiveParameters().isEmpty()) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (call.receiveParameters()["id_copy"] != null)
                    filterConditions.add("id_copy = " + call.receiveParameters()["id_copy"])
                if (call.receiveParameters()["change"] != null)
                    filterConditions.add("change = " + call.receiveParameters()["change"])
                if (call.receiveParameters()["date"] != null)
                    filterConditions.add("date = " + call.receiveParameters()["date"])
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"BankHistory\"$filter"
                )
            val bankHistory = ArrayList<BankHistory>()
            while (resultSet.next())
                bankHistory.add(
                    BankHistory(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
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
                        resultSet.getString(4)
                    )
                )
            call.respondText(Json.encodeToString(bankHistory))
        }
        post("/insert") {
            val idCopy = call.receiveParameters()["id_copy"] ?: return@post call.respondText(
                "Missing or malformed id_copy",
                status = HttpStatusCode.BadRequest
            )
            val change = call.receiveParameters()["change"] ?: return@post call.respondText(
                "Missing or malformed change",
                status = HttpStatusCode.BadRequest
            )
            val date = call.receiveParameters()["date"] ?: return@post call.respondText(
                "Missing or malformed date",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"BankHistory\" (id_copy, change, date)" +
                        " VALUES ($idCopy, $change, $date)"
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
                if (call.receiveParameters()["id_copy"] != null)
                    setParameters.add("id_copy = " + call.receiveParameters()["id_copy"])
                if (call.receiveParameters()["change"] != null)
                    setParameters.add("change = " + call.receiveParameters()["change"])
                if (call.receiveParameters()["date"] != null)
                    setParameters.add("date = " + call.receiveParameters()["date"])
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

