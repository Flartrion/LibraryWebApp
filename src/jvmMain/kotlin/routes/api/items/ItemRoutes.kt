package routes.api.items

import io.ktor.server.routing.*

fun Route.itemRoutes() {
    route("items") {
        itemGetAll()
        itemGet()
        itemCreate()
        itemUpdate()
        itemDelete()
    }
}