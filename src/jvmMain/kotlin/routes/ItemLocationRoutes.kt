package routes

import ItemLocation
import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.itemLocationRouting() {
    route("/itemLocation") {
        get {
            if (call.request.cookies["role"] != "admin" && call.request.cookies["role"] != "user") return@get call.respondText(
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
                if (parameters["id_storage"] != null)
                    filterConditions.add("id_storage = '" + parameters["id_storage"] + "'")
                if (parameters["amount"] != null)
                    filterConditions.add("amount = '" + parameters["amount"] + "'")
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"ItemLocation\"$filter" +
                            "ORDER BY id_storage ASC"
                )
            val itemLocations = ArrayList<ItemLocation>()
            while (resultSet.next())
                itemLocations.add(
                    ItemLocation(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                    )
                )
            call.respondText(Json.encodeToString(itemLocations))
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
            val idStorage = parameters["id_storage"] ?: return@post call.respondText(
                "Missing or malformed id_storage",
                status = HttpStatusCode.BadRequest
            )
            val amount = parameters["amount"] ?: return@post call.respondText(
                "Missing or malformed amount",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"ItemLocation\" (id_item, id_storage, amount)" +
                        " VALUES ('$idItem', '$idStorage', '$amount')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            if (call.request.cookies["role"] != "admin") return@post call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val idItem = call.parameters["id_item"] ?: return@post call.respondText(
                "Missing or malformed id_item",
                status = HttpStatusCode.BadRequest
            )
            val idStorage = call.parameters["id_storage"] ?: return@post call.respondText(
                "Missing or malformed id_storage",
                status = HttpStatusCode.BadRequest
            )
            val amount = call.receiveParameters()["amount"] ?: return@post call.respondText(
                "Missing or malformed parameters",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"ItemLocation\"" +
                        " SET amount = '$amount'" +
                        " WHERE id_item = '$idItem' AND id_storage = '$idStorage'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            if (call.request.cookies["role"] != "admin") return@delete call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val idItem = call.parameters["id_item"] ?: return@delete call.respondText(
                "Missing or malformed id_item",
                status = HttpStatusCode.BadRequest
            )
            val idStorage = call.parameters["id_storage"] ?: return@delete call.respondText(
                "Missing or malformed id_storage",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"ItemLocation\"" +
                        " WHERE id_item = '$idItem' AND id_storage = '$idStorage'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerItemLocationRoutes() {
    routing {
        itemLocationRouting()
    }
}