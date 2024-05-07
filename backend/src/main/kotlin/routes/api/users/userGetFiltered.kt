package routes.api.users

import dataType.User
import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import db.model.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.userGetFiltered() {
    get {
//        call.respond("Access success")
        val search = call.receive<User>()
        val resultSet = dbQuery {
            UserEntity.find {
                Users.role eq search.role
                Users.fullName like search.fullName
                Users.email like search.email
                Users.phoneNumber like search.phoneNumber
                Users.dob eq search.dateOfBirth.toLocalDate()
            }.sortedBy { Users.fullName }
        }

        if (resultSet.isNotEmpty())
            call.respondText(
                Json.encodeToString(resultSet.map { it.entityToUser() }),
                ContentType.Application.Json,
                HttpStatusCode.OK
            ) else
            call.respond(HttpStatusCode.NotFound)
    }
}