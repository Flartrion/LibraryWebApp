package routes.api.items

import dataType.Item
import db.DatabaseSingleton.dbQuery
import db.entity.ItemEntity
import db.model.Items
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.itemGetFiltered() {
    get {
        val search = call.receive<Item>()
        val resultSet = dbQuery {
            ItemEntity.find {
                Items.isbn like search.isbn
                Items.rlbc like search.rlbc
                Items.title like search.title
                Items.authors like search.authors
                Items.details like search.details
                Items.language like search.language
                Items.type like search.type
            }.sortedByDescending { it.authors }
        }

        if (resultSet.isNotEmpty())
            call.respondText(
                Json.encodeToString(resultSet.map { it.entityToItem() }),
                ContentType.Application.Json,
                HttpStatusCode.OK
            ) else
            call.respond(HttpStatusCode.NotFound)
    }
}