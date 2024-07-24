package routes.api.items

import db.DatabaseSingleton.dbQuery
import db.entity.ItemEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.itemDelete() {
    delete("/{id}") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")
            return@delete
        }

        val role = principal.payload.getClaim("role").asInt()
        if (role >= 5) {
            call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
            return@delete
        }
        val id = call.parameters["id"] ?: return@delete call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )

        val success = dbQuery {
            ItemEntity.findById(UUID.fromString(id))?.delete()
        }

        if (success != null)
            call.respond(HttpStatusCode.OK, "Item deleted!")
        else
            call.respond(HttpStatusCode.BadRequest, "Item not found!")
    }
}