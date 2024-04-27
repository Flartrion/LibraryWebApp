package routes.api.items

import db.dao.daoItems
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.itemDelete() {
    delete("{id}") {
        if (call.request.cookies["role"] != "admin") return@delete call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )
        val id = call.parameters["id"] ?: return@delete call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        val success = daoItems.delete(UUID.fromString(id))
        if (success)
            call.respond(HttpStatusCode.OK)
        else
            call.respond(HttpStatusCode.BadRequest)
    }
}