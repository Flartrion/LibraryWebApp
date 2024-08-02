package routes.api.users

import dataType.User
import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDate
import java.util.*

fun Route.userUpdate() {
    post("update") {
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
            // Allow for editing of own data at least for non-admins, ya?
            val role = principal.payload.getClaim("role").asInt()
            val principalId = principal.payload.getClaim("id").asString()
            if (role >= 5 && principalId != id) {
                call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
                return@post
            }

            val newEntity = call.receive<User>()
            val editedEntity = dbQuery {
                UserEntity.findById(UUID.fromString(newEntity.id))
            }
//            println(newEntity)
            if (editedEntity != null) {
                dbQuery {
                    editedEntity.apply {
                        this.fullName = newEntity.fullName
                        this.role = newEntity.role.toInt()
                        this.email = newEntity.email
                        this.phoneNumber = newEntity.phoneNumber
                        this.dob = LocalDate.parse(newEntity.dob!!)
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