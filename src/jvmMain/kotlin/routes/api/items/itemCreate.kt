package routes.api.items

import dataType.Item
import db.DatabaseSingleton.dbQuery
import db.entity.ItemEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemCreate() {
    post("/new") {
        // Add proper auth
        if (call.request.cookies["role"] != "admin") return@post call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )

        val newEntity = call.receive<Item>()
        val success = dbQuery {
            ItemEntity.new {
                this.isbn = newEntity.isbn
                this.rlbc = newEntity.rlbc
                this.title = newEntity.title
                this.authors = newEntity.authors
                this.type = newEntity.type
                this.language = newEntity.language
                this.details = newEntity.details
            }
        }
        call.respond(HttpStatusCode.OK)
    }
}