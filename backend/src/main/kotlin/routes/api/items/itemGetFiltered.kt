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
import org.jetbrains.exposed.sql.and
import support.filterWrap


fun Route.itemGetFiltered() {
    post("get") {
        try {
            val search = call.receive<Item>()
            val searchFilter = search.copy(
//                id = filterWrap(search.id),
                isbn = filterWrap(search.isbn),
                rlbc = filterWrap(search.rlbc),
                title = filterWrap(search.title),
                details = filterWrap(search.details),
                authors = filterWrap(search.authors),
                type = filterWrap(search.type),
                language = filterWrap(search.language),
            )

            val resultSet = dbQuery {
                ItemEntity.find {
                    (Items.isbn like searchFilter.isbn) and
                            (Items.rlbc like searchFilter.rlbc) and
                            (Items.title like searchFilter.title) and
                            (Items.authors like searchFilter.authors) and
                            (Items.details like searchFilter.details) and
                            (Items.language like searchFilter.language) and
                            (Items.type like searchFilter.type)
                }.sortedByDescending { it.authors }
            }

            if (resultSet.isNotEmpty()) {
                call.respondText(
                    Json.encodeToString(resultSet.map { it.entityToItem() }),
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