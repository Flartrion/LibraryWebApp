package routes.api.items

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.itemRoutes() {
    route("items") {
        itemGetFiltered()
        itemGet()
        authenticate("auth-jwt") {
            itemCreate()
            itemUpdate()
            itemDelete()
        }
    }
}