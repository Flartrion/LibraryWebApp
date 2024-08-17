package routes.api.bankHistory

import db.DatabaseSingleton.dbQuery
import db.entity.BankHistoryEntryEntity
import db.model.ItemLocations
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.*

fun Route.bankHistoryDelete() {
    delete("delete/{id}") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")
            return@delete
        }

        val role = principal.payload.getClaim("role").asInt()
        if (role >= 10) {
            call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
            return@delete
        }

        try {
            val id = call.parameters["id"].toString()

            val returnedValue = dbQuery {
                val entry = BankHistoryEntryEntity.findById(UUID.fromString(id))
                if (entry != null) {
                    val storage = entry.storage
                    val item = entry.item
                    val itemLocEntry = ItemLocations.selectAll()
                        .where {
                            // 'Tis a primary key, should only ever be a single entry or none at all
                            (ItemLocations.item eq item.id) and
                                    (ItemLocations.storage eq storage.id)
                        }

                    if (itemLocEntry.empty()) {
                        throw IllegalArgumentException("Deleting entry for which no itemlocation exists!")
                    } else {
                        ItemLocations.update(
                            {
                                (ItemLocations.item eq item.id) and
                                        (ItemLocations.storage eq storage.id)
                            },
                        ) {
                            with(SqlExpressionBuilder) {
                                it[amount] = amount - entry.change
                            }
                        }
                    }

                    entry.delete()
                } else
                    null
            }
            if (returnedValue != null) {
                call.respond(HttpStatusCode.OK, "Deletion successful")
                return@delete
            } else {
                call.respond(HttpStatusCode.BadRequest, "Entry with specified ID not found")
                return@delete
            }

        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@delete
        }
    }
}