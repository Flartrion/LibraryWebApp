package routes.api.rents

import dataType.Rent
import dataType.RentRequestStatus
import db.DatabaseSingleton.dbQuery
import db.entity.ItemEntity
import db.entity.RentEntity
import db.entity.StorageEntity
import db.entity.UserEntity
import db.model.Rents.item
import db.model.Rents.status
import db.model.Rents.user
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.and
import java.util.*

fun Route.rentCreate() {
    post("new") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")
            return@post
        }
        val idUser = principal.payload.getClaim("id").asString()

        try {
            val newEntity = call.receive<Rent>()
            val noSimilar = dbQuery {
                RentEntity.find {
                    (item eq UUID.fromString(newEntity.idItem)) and
                            (user eq UUID.fromString(idUser)) and
                            (status eq RentRequestStatus.ACCEPTED.ordinal)
                }.empty()
            }
            if (noSimilar) {
                dbQuery {
                    val newRent = RentEntity.new {
                        this.item = ItemEntity[UUID.fromString(newEntity.idItem)]
                        this.user = UserEntity[UUID.fromString(idUser)]
                        this.storage = StorageEntity[UUID.fromString(newEntity.idStorage)]
                        this.status = RentRequestStatus.ACCEPTED.ordinal
                        val now = Calendar.getInstance()
                        this.dateFrom = LocalDate(now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DAY_OF_MONTH])
                        this.dateUntil = LocalDate.parse(newEntity.dateUntil)
                        this.dateStatus = LocalDate(now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DAY_OF_MONTH])
                    }
//                    println(newRent.toDataclass())
                }
                call.respond(HttpStatusCode.Created, "Rent request placed!")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Rent request already placed")
                return@post
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
        call.respond(HttpStatusCode.Created, "Item added to database")
        return@post
    }
}