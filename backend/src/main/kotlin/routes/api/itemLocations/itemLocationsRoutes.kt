package routes.api.itemLocations

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.itemLocationRoutes() {
    authenticate("auth-jwt") {
        route("itemLocations") {
            itemLocationGetItem()
        }
    }
}
