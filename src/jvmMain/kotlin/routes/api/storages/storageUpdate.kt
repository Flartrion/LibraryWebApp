package routes.api.storages

import dataType.Storage
import db.DatabaseSingleton.dbQuery
import db.entity.StorageEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.storageUpdate() {
    post("/update/{id}") {
        if (call.request.cookies["role"] != "admin") return@post call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )
        val id = call.parameters["id"] ?: return@post call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )

        val updEntity = call.receive<Storage>()

        val success = dbQuery {
            StorageEntity.findById(UUID.fromString(id))?.apply {
                address = updEntity.address
            }
        }
        if (success != null) {
            call.respond(HttpStatusCode.OK)
        } else
            call.respond(HttpStatusCode.NotFound)
    }
}