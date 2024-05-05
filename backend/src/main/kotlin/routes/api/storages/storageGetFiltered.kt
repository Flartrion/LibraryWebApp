package routes.api.storages

import dataType.Storage
import db.DatabaseSingleton.dbQuery
import db.entity.StorageEntity
import db.model.Storages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.storageGetFiltered() {
    get {
        if (call.request.cookies["role"] != "admin" && call.request.cookies["role"] != "user") return@get call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )

        val search = call.receive<Storage>()
        val resultSet = dbQuery {
            StorageEntity.find {
                Storages.address like search.address
            }.sortedByDescending { it.address }
        }

        if (resultSet.isNotEmpty())
            call.respondText(
                Json.encodeToString(resultSet.map { it.entityToStorage() }),
                ContentType.Application.Json,
                HttpStatusCode.OK
            ) else
            call.respond(HttpStatusCode.NotFound)
    }
}