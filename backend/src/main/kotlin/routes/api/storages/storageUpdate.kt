package routes.api.storages

import dataType.Storage
import db.DatabaseSingleton.dbQuery
import db.entity.StorageEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.storageUpdate() {
    post("update/{id}") {
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
            val newEntity = call.receive<Storage>()
            val editedEntity = dbQuery {
                StorageEntity.findById(UUID.fromString(newEntity.id))
            }
//            println(newEntity)
            if (editedEntity != null) {
                dbQuery {
                    editedEntity.apply {
                        this.address = newEntity.address
                    }
//                    println(editedEntity.entityToItem())
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