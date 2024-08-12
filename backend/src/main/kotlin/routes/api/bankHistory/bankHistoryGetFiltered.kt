package routes.api.bankHistory

import dataType.BankHistoryEntry
import db.DatabaseSingleton.dbQuery
import db.entity.BankHistoryEntryEntity
import db.entity.ItemEntity
import db.entity.StorageEntity
import db.model.BankHistory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import java.util.*

fun Route.bankHistoryGetFiltered() {
    post("get") {
        try {
            val search = call.receive<BankHistoryEntry>()
            val resultSet = dbQuery {
                val item = ItemEntity.findById(UUID.fromString(search.idItem))
                val storage = StorageEntity.findById(UUID.fromString(search.idStorage))
                BankHistoryEntryEntity.find {
                    (if (item != null) BankHistory.item eq item.id else Op.TRUE) and
                            (if (storage != null) BankHistory.storage eq storage.id else Op.TRUE)
                }.sortedByDescending { it.date }
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