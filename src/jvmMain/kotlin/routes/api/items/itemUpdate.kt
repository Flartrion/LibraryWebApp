package routes.api.items

import Item
import db.dao.daoItems
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemUpdate() {
    post("/update/{id}") {

        if (call.request.cookies["role"] != "admin") return@post call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )

        val updItem = call.receive<Item>()
        val success = daoItems.edit(updItem)
        if (success)
            call.respond(HttpStatusCode.OK)
        else
            call.respond(HttpStatusCode.BadRequest)
    }
}