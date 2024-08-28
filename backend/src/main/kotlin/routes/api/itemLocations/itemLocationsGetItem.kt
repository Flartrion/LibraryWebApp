package routes.api.itemLocations

import dataType.AvailabilityEntry
import db.DatabaseSingleton.dbQuery
import db.model.ItemLocations
import db.model.Storages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.JoinType

fun Route.itemLocationGetItem() {
    post("get/{id}") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")
            return@post
        }

        try {
            val idItemQuery = call.parameters["id"]
            if (idItemQuery == null) {
                call.respond(HttpStatusCode.BadRequest, "Malformed or missing id")
                return@post
            }

            dbQuery {
                val query = ItemLocations.join(
                    Storages,
                    JoinType.INNER,
                    onColumn = ItemLocations.storage,
                    otherColumn = Storages.id
                ).select(ItemLocations.columns + Storages.address)

                val results = query.map {
                    AvailabilityEntry(
                        it[ItemLocations.item].value.toString(),
                        it[ItemLocations.storage].value.toString(),
                        it[ItemLocations.amount],
                        it[Storages.address]
                    )
                }
//                println(results)

                call.respond(HttpStatusCode.OK, results)
            }


        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
    }
}