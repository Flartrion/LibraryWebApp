package routes.api.bankHistory

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.bankHistoryRoutes() {
    authenticate("auth-jwt") {
        route("bankHistory") {
            bankHistoryAdd()
            bankHistoryGetFiltered()
        }
    }
}