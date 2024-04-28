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
        if (call.request.cookies["role"] != "admin") return@post call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )
        val newEntity = call.receive<User>()

        val success = dbQuery {
            UserEntity.new {
                fullName = newEntity.full_name
                role = newEntity.role
                phoneNumber = newEntity.phone_number
                dob = newEntity.date_of_birth.toLocalDate()
                email = newEntity.email
            }
        }

        call.respond(HttpStatusCode.OK)
    }
}