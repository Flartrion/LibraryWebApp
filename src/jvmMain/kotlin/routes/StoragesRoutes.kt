package routes

import DataBase.statement
import Storages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.text.get

fun Route.storagesRouting() {
    route("/storages") {
        get {
            if (call.request.cookies["role"] != "admin" && call.request.cookies["role"] != "user") return@get call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val address =
                if (call.request.queryParameters["address"] != null) " WHERE " + call.request.queryParameters["address"] else ""
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Facilities\".\"Storages\"$address" +
                            " ORDER BY id_storage ASC"
                )
            val storages = ArrayList<Storages>()
            while (resultSet.next())
                storages.add(Storages(resultSet.getString(1), resultSet.getString(2)))
            call.respondText(Json.encodeToString(storages))
        }
        get("{id}") {
            if (call.request.cookies["role"] != "admin" && call.request.cookies["role"] != "user") return@get call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
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
            if (call.request.cookies["role"] != "admin") return@post call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val address = call.receiveParameters()["address"] ?: return@post call.respondText(
                "Missing or malformed parameters",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Facilities\".\"Storages\" (address)" +
                        " VALUES ('$address')"
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
            val address = call.receiveParameters()["address"] ?: return@post call.respondText(
                "Missing or malformed parameters",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "UPDATE \"Facilities\".\"Storages\"" +
                        " SET address = '$address'" +
                        " WHERE id_storage = $id"
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
                "DELETE FROM \"Facilities\".\"Storages\"" +
                        " WHERE id_storage = $id"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}