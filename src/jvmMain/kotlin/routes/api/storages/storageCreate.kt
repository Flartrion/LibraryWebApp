package routes.api.storages

import dataType.Storage
import db.DatabaseSingleton.dbQuery
import db.entity.StorageEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.storageCreate() {
    post("/new") {
        if (call.request.cookies["role"] != "admin") return@post call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )

        val newEntity = call.receive<Storage>()

        val success = dbQuery {
            StorageEntity.new {
                address = newEntity.address
            }
        }

        call.respond(HttpStatusCode.OK)
    }
}