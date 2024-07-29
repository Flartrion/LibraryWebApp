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
import java.util.*

fun Route.userUpdate() {
    post("/update/{id}") {
        val id = call.parameters["id"] ?: return@post call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        val updEntity = call.receive<User>()
        val success = dbQuery {
            UserEntity.findById(UUID.fromString(id))?.apply {
                fullName = updEntity.fullName
                role = updEntity.role
                phoneNumber = updEntity.phoneNumber
                dob = updEntity.dob.toLocalDate()
                email = updEntity.email
            }
        }

        if (success != null)
            call.respond(HttpStatusCode.OK)
        else
            call.respond(HttpStatusCode.NotFound)
    }
}