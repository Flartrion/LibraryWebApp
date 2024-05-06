package routes.api.users

import dataType.User
import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.toLocalDate

fun Route.userCreate() {
    post("/new") {
        val newEntity = call.receive<User>()

        val success = dbQuery {
            UserEntity.new {
                fullName = newEntity.fullName
                role = newEntity.role
                phoneNumber = newEntity.phoneNumber
                dob = newEntity.dateOfBirth.toLocalDate()
                email = newEntity.email
            }
        }

        call.respond(HttpStatusCode.OK)
    }
}