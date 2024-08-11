package routes.api.users

import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

fun Route.userGet() {
    post("get/{id}") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")
            return@post
        }

        try {
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            // Allow for viewing of own data at least for non-admins, ya?
            val role = principal.payload.getClaim("role").asInt()
            val principalId = principal.payload.getClaim("id").asString()
            if (role >= 5 && principalId != id) {
                call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
                return@post
            }


            val user = dbQuery { UserEntity.findById(UUID.fromString(id)) }
            if (user != null) {
                call.respondText(
                    Json.encodeToString(user.toDataclass()),
                    ContentType.Application.Json,
                    HttpStatusCode.OK
                )
                return@post
            } else {
                call.respond(HttpStatusCode.NotFound, "User with such ID not found")
                return@post
            }
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
    }
}