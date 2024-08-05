package routes.api.users

import dataType.User
import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import db.model.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.or

fun Route.userCreate() {
    post("/new") {
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
            val newEntity = call.receive<User>()
            val noSimilar = dbQuery {
                UserEntity.find {
                    // No equalities allowed here, since this doubles as login data
                    // (for I am too lazy to implement proper password safety)
                    // Anything else is allowed to be exact same as some other table row.
                    // (Clones with same full names, dates of birth and system privileges? Why not)
                    (Users.phoneNumber eq newEntity.phoneNumber) or
                            (Users.email eq newEntity.email)
                }.empty()
            }
//            println("Pre-parse string: ${newEntity.dob!!}")
//            println("Post-parse string: ${LocalDate.parse(newEntity.dob)}")
            if (noSimilar) {
                dbQuery {
                    UserEntity.new {
                        this.fullName = newEntity.fullName
                        this.role = newEntity.role.toInt()
                        this.email = newEntity.email
                        this.phoneNumber = newEntity.phoneNumber
                        this.dob = LocalDate.parse(newEntity.dob!!)
                    }
                }
                call.respond(HttpStatusCode.Created, "Item added to database")
                return@post
            } else {
                call.respond(HttpStatusCode.BadRequest, "User already exists")
                return@post
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
        call.respond(HttpStatusCode.OK, "end of pipeline")
        return@post
    }
}