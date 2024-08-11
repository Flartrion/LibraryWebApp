package routes.api.storages

import dataType.Storage
import db.DatabaseSingleton.dbQuery
import db.entity.StorageEntity
import db.model.Storages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.storageCreate() {
    post("/new") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null)
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")

        val role = principal!!.payload.getClaim("role").asInt()
        if (role >= 5) {
            call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
            return@post
        }

        try {
            val newEntity = call.receive<Storage>()
            val noSimilar = dbQuery {
                StorageEntity.find {
                    Storages.address eq newEntity.address
                }.empty()
            }
            if (noSimilar) {
                dbQuery {
                    StorageEntity.new {
                        this.address = newEntity.address
                    }
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Storage already exists")
                return@post
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
        call.respond(HttpStatusCode.Created, "Storage added to database")
        return@post
    }
}