package routes.api.bankHistory

import dataType.BankHistoryEntry
import db.DatabaseSingleton.dbQuery
import db.entity.BankHistoryEntryEntity
import db.entity.ItemEntity
import db.entity.StorageEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun Route.bankHistoryAdd() {
    post("new") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")
            return@post
        }

        val role = principal.payload.getClaim("role").asInt()
        if (role >= 10) {
            call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
            return@post
        }

        try {
            val entry = call.receive<BankHistoryEntry>()
            val storage = dbQuery {
                StorageEntity.findById(UUID.fromString(entry.idStorage))
            }
            val item = dbQuery {
                ItemEntity.findById(UUID.fromString(entry.idItem))
            }
            val returnedValue = dbQuery {
                if (storage != null && item != null) {
                    return@dbQuery BankHistoryEntryEntity.new {
                        this.item = item
                        this.storage = storage
                        this.date = LocalDate.parse(entry.date)
                        this.change = entry.change.toInt()
                    }.toDataclass()
                } else
                    null
            }
            if (returnedValue != null) {
                call.respond(HttpStatusCode.Created, Json.encodeToString(returnedValue))
                return@post
            } else {
                call.respond(HttpStatusCode.BadRequest, "Item or storage with specified IDs not found")
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