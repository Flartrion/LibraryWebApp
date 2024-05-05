package routes.api.items

import io.ktor.server.routing.*

fun Route.itemRoutes() {
    route("items") {
        itemGetFiltered()
        itemGet()
        itemCreate()
        itemUpdate()
        itemDelete()
    }
}