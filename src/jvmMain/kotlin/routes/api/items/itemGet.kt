package routes.api.items

import db.dao.daoItems
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun Route.itemGet() {
    get("{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        val items = daoItems.get(UUID.fromString(id))
        call.respondText(Json.encodeToString(items))
    }
}