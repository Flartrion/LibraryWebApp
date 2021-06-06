package routes

import Copies
import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.copiesRouting() {
    route("/copies") {
        get {
            var filter: String
            if (!call.receiveParameters().isEmpty()) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (call.receiveParameters()["id_item"] != null)
                    filterConditions.add("id_item = " + call.receiveParameters()["id_item"])
                if (call.receiveParameters()["tome"] != null)
                    filterConditions.add("tome = " + call.receiveParameters()["tome"])
                if (call.receiveParameters()["language"] != null)
                    filterConditions.add("language = " + call.receiveParameters()["language"])
                if (call.receiveParameters()["bank"] != null)
                    filterConditions.add("bank = " + call.receiveParameters()["bank"])
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Copies\"$filter"
                )
            val copies = ArrayList<Copies>()
            while (resultSet.next())
                copies.add(
                    Copies(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                    )
                )
            call.respondText(Json.encodeToString(copies))
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Copies\"" +
                            " WHERE id_copy = '$id'"
                )
            val copies = ArrayList<Copies>()
            while (resultSet.next())
                copies.add(
                    Copies(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                    )
                )
            call.respondText(Json.encodeToString(copies))
        }
        post("/insert") {
            val idItem = call.receiveParameters()["id_item"] ?: return@post call.respondText(
                "Missing or malformed id_item",
                status = HttpStatusCode.BadRequest
            )
            val tome = call.receiveParameters()["tome"] ?: return@post call.respondText(
                "Missing or malformed tome",
                status = HttpStatusCode.BadRequest
            )
            val language = call.receiveParameters()["language"] ?: return@post call.respondText(
                "Missing or malformed language",
                status = HttpStatusCode.BadRequest
            )
            val bank = call.receiveParameters()["bank"] ?: return@post call.respondText(
                "Missing or malformed bank",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Copies\" (id_item, tome, language, bank)" +
                        " VALUES ($idItem, $tome, $language, $bank)"
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
                if (call.receiveParameters()["id_item"] != null)
                    setParameters.add("id_item = " + call.receiveParameters()["id_item"])
                if (call.receiveParameters()["tome"] != null)
                    setParameters.add("tome = " + call.receiveParameters()["tome"])
                if (call.receiveParameters()["language"] != null)
                    setParameters.add("language = " + call.receiveParameters()["language"])
                if (call.receiveParameters()["bank"] != null)
                    setParameters.add("bank = " + call.receiveParameters()["bank"])
                setExpression += setParameters[0]
                setParameters.removeAt(0)
                for (i in setParameters) {
                    setExpression += ", $i"
                }
            } else
                return@post call.respondText("Missing or malformed parameters", status = HttpStatusCode.BadRequest)
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"Copies\"" +
                        setExpression +
                        " WHERE id_copy = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Copies\"" +
                        " WHERE id_copy = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerCopiesRoutes() {
    routing {
        copiesRouting()
    }
}