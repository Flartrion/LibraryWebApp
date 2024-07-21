package routes.api.items

import dataType.Item
import db.DatabaseSingleton.dbQuery
import db.entity.ItemEntity
import db.model.Items.authors
import db.model.Items.details
import db.model.Items.isbn
import db.model.Items.language
import db.model.Items.rlbc
import db.model.Items.title
import db.model.Items.type
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.and

fun Route.itemCreate() {
    post("/new") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null)
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")

        val role = principal!!.payload.getClaim("role").asInt()
        if (role >= 5) {
            call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
            return@post
        }

        try {
            val newEntity = call.receive<Item>()
            val noSimilar = dbQuery {
                ItemEntity.find {
                    (isbn eq newEntity.isbn) and
                    (rlbc eq newEntity.rlbc) and
                    (title eq newEntity.title) and
                    (authors eq newEntity.authors) and
                    (type eq newEntity.type) and
                    (language eq newEntity.language) and
                    (details eq newEntity.details)
                }.empty()
            }
            if (noSimilar) {
                dbQuery {
                    ItemEntity.new {
                        this.isbn = newEntity.isbn
                        this.rlbc = newEntity.rlbc
                        this.title = newEntity.title
                        this.authors = newEntity.authors
                        this.type = newEntity.type
                        this.language = newEntity.language
                        this.details = newEntity.details
                    }
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Item already exists")
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