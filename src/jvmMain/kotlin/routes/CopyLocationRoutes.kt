package routes

import CopyLocation
import DataBase.statement
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.copyLocationRouting() {
    route("/copyLocation") {
        get {
            var filter: String
            if (!call.receiveParameters().isEmpty()) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (call.receiveParameters()["id_copy"] != null)
                    filterConditions.add("id_copy = " + call.receiveParameters()["id_copy"])
                if (call.receiveParameters()["id_storage"] != null)
                    filterConditions.add("id_storage = " + call.receiveParameters()["id_storage"])
                if (call.receiveParameters()["amount"] != null)
                    filterConditions.add("amount = " + call.receiveParameters()["amount"])
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"CopyLocation\"$filter"
                )
            val copyLocations = ArrayList<CopyLocation>()
            while (resultSet.next())
                copyLocations.add(
                    CopyLocation(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                    )
                )
            call.respondText(Json.encodeToString(copyLocations))
        }
        post("/insert") {
            val idCopy = call.receiveParameters()["id_copy"] ?: return@post call.respondText(
                "Missing or malformed id_copy",
                status = HttpStatusCode.BadRequest
            )
            val idStorage = call.receiveParameters()["id_storage"] ?: return@post call.respondText(
                "Missing or malformed id_storage",
                status = HttpStatusCode.BadRequest
            )
            val amount = call.receiveParameters()["amount"] ?: return@post call.respondText(
                "Missing or malformed amount",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"CopyLocation\" (id_copy, id_storage, amount)" +
                        " VALUES ($idCopy, $idStorage, $amount)"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            val idCopy = call.parameters["id_copy"] ?: return@post call.respondText(
                "Missing or malformed id_copy",
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
                "UPDATE \"Inventory\".\"CopyLocation\"" +
                        " SET amount = '$amount'" +
                        " WHERE id_copy = $idCopy AND id_storage = $idStorage"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val idCopy = call.parameters["id_copy"] ?: return@delete call.respondText(
                "Missing or malformed id_copy",
                status = HttpStatusCode.BadRequest
            )
            val idStorage = call.parameters["id_storage"] ?: return@delete call.respondText(
                "Missing or malformed id_storage",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"CopyLocation\"" +
                        " WHERE id_copy = $idCopy AND id_storage = $idStorage"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerCopyLocationRoutes() {
    routing {
        copyLocationRouting()
    }
}