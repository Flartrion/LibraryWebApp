package routes.api.rents

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.rentRoutes() {
    route("rents") {
        authenticate("auth-jwt") {
            rentCreate()
        }
    }
}