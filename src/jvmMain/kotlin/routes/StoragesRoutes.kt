package routes

import DataBase.statement
import Storages
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.storagesRouting() {
    route("/storages") {
        get {
            val address = if (call.parameters["address"] != null) " WHERE " + call.parameters["address"] else ""
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Facilities\".\"Storages\"$address"
                )
            val storages = ArrayList<Storages>()
            while (resultSet.next())
                storages.add(Storages(resultSet.getInt(1).toString(), resultSet.getString(2)))
            call.respondText(Json.encodeToString(storages))
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Facilities\".\"Storages\"" +
                            " WHERE id_storage = $id"
                )
            val storages = ArrayList<Storages>()
            while (resultSet.next())
                storages.add(Storages(resultSet.getInt(1).toString(), resultSet.getString(2)))
            call.respondText(Json.encodeToString(storages))
        }
        post("/insert") {
            val address = call.receiveParameters()["address"] ?: return@post call.respondText(
                "Missing or malformed parameters",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Facilities\".\"Storages\" (address)" +
                        " VALUES ('$address')"
            )
        }
        post("/update/{id}") {
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val address = call.receiveParameters()["address"] ?: return@post call.respondText(
                "Missing or malformed parameters",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "UPDATE \"Facilities\".\"Storages\"" +
                        " SET address = '$address'" +
                        " WHERE id_storage = $id"
            )
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Facilities\".\"Storages\"" +
                        " WHERE id_storage = $id"
            )
        }
    }
}

fun Application.registerStoragesRoutes() {
    routing {
        storagesRouting()
    }
}