package routes.api.users

import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

fun Route.userGet() {
    get("/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        val user = dbQuery {
            UserEntity.findById(UUID.fromString(id))
        }
        if (user != null) {
            call.respondText(
                Json.encodeToString(user.entityToUser()),
                ContentType.Application.Json,
                HttpStatusCode.Found
            )
        } else
            call.respond(HttpStatusCode.NotFound)
    }
}