package routes.api.storages

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.storagesRouting() {
    route("storages") {
        storageGetFiltered()
        storageGet()
        authenticate("auth-jwt") {
            storageCreate()
            storageUpdate()
            storageDelete()
        }
    }
}