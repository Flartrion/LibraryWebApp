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
import support.filterWrap

fun Route.storageGetFiltered() {
    post("get") {
        try {
            val search = call.receive<Storage>()
            val searchFilter = search.copy(
//                id = filterWrap(search.id),
                address = filterWrap(search.address),
            )

            val resultSet = dbQuery {
                StorageEntity.find {
                    (Storages.address like searchFilter.address)
                }.sortedByDescending { it.address }
            }

            if (resultSet.isNotEmpty()) {
                call.respondText(
                    Json.encodeToString(resultSet.map { it.toDataclass() }),
                    ContentType.Application.Json,
                    HttpStatusCode.OK
                )
                return@post
            } else {
                call.respond(HttpStatusCode.NotFound, "No such items found in database")
                return@post
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
    }
}