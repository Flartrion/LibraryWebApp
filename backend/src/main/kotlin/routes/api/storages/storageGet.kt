package routes.api.storages

import dataType.Storage
import db.DatabaseSingleton.dbQuery
import db.entity.StorageEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.collections.ArrayList

fun Route.storageGet() {
    get("/{id}") {
        if (call.request.cookies["role"] != "admin" && call.request.cookies["role"] != "user") return@get call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        val resultSet = dbQuery {
            StorageEntity.findById(UUID.fromString(id))
        }

        if (resultSet != null)
            call.respondText(
                Json.encodeToString(resultSet.entityToStorage()),
                ContentType.Application.Json,
                HttpStatusCode.OK
            )
        else
            call.respond(HttpStatusCode.NotFound)
    }
}