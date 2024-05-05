package routes.api.storages

import io.ktor.server.routing.*

fun Route.storagesRouting() {
    route("/storages") {
        storageGetFiltered()
        storageGet()
        storageCreate()
        storageUpdate()
        storageDelete()
    }
}