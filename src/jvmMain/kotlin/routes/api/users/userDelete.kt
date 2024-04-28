package routes.api.users

import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userDelete() {
    delete("{id}") {
        if (call.request.cookies["role"] != "admin") return@delete call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )
        val id = call.parameters["id"] ?: return@delete call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )

        val success = dbQuery {
            UserEntity.findById(UUID.fromString(id))?.delete()
        }

        if (success != null)
            call.respond(HttpStatusCode.OK)
        else
            call.respond(HttpStatusCode.NotFound)
    }
}