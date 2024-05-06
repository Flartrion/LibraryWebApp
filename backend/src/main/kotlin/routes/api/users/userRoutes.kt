package routes.api.users

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    authenticate("auth-jwt") {
        route("/users") {
            userGetFiltered()
            userGet()
            userCreate()
            userUpdate()
            userDelete()
        }
    }
}
