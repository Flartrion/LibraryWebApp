package routes.api.items

import dataType.Item
import db.DatabaseSingleton.dbQuery
import db.entity.ItemEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.itemUpdate() {
    post("update") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")
            return@post
        }

        val role = principal.payload.getClaim("role").asInt()
        if (role >= 5) {
            call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
            return@post
        }

        try {
            val newEntity = call.receive<Item>()
            val editedEntity = dbQuery {
                ItemEntity.findById(UUID.fromString(newEntity.id))
            }
            println(newEntity)
            if (editedEntity != null) {
                dbQuery {
                    editedEntity.apply {
                        this.isbn = newEntity.isbn
                        this.rlbc = newEntity.rlbc
                        this.title = newEntity.title
                        this.type = newEntity.type
                        this.language = newEntity.language
                        this.details = newEntity.details
                        this.authors = newEntity.authors
                    }
                    println(editedEntity.entityToItem())
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Item not found")
                return@post
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
        call.respond(HttpStatusCode.OK, "Item updated!")
        return@post
    }
}