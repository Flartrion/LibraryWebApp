package routes.api.users

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    authenticate("auth-jwt") {
        route("users") {
            userGetFiltered()
            userGet()
            userCreate()
            userUpdate()
            userDelete()
        }
    }
}
