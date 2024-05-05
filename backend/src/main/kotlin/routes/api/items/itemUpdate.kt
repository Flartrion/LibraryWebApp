package routes.api.items

import dataType.Item
import db.DatabaseSingleton.dbQuery
import db.entity.ItemEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Route.itemUpdate() {
    post("/update/{id}") {

        if (call.request.cookies["role"] != "admin") return@post call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )
        val id = call.parameters["id"] ?: return@post call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )

        val updEntity = call.receive<Item>()
        val success = dbQuery {
            ItemEntity.findById(UUID.fromString(id))?.apply {
                isbn = updEntity.isbn
                rlbc = updEntity.rlbc
                type = updEntity.type
                title = updEntity.title
                authors = updEntity.authors
                language = updEntity.language
                details = updEntity.details

            }
        }
        if (success != null)
            call.respond(HttpStatusCode.OK)
        else
            call.respond(HttpStatusCode.BadRequest)
    }
}