package routes.api.storages

import db.DatabaseSingleton.dbQuery
import db.entity.StorageEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun Route.storageGet() {
    post("get/{id}") {
        try {
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val item = dbQuery { StorageEntity.findById(UUID.fromString(id)) }
            if (item != null) {
                call.respondText(
                    Json.encodeToString(item.toDataclass()),
                    ContentType.Application.Json,
                    HttpStatusCode.OK
                )
                return@post
            } else {
                call.respond(HttpStatusCode.NotFound, "Storage with such ID not found")
                return@post
            }
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
    }
}