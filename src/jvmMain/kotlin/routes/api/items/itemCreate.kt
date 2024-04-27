package routes.api.items

import Item
import db.dao.daoItems
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemCreate() {
    post("/add") {
        // Add proper auth
        if (call.request.cookies["role"] != "admin") return@post call.respondText(
            "Access is forbidden",
            status = HttpStatusCode.Forbidden
        )

        val newItem = call.receive<Item>()
        val success = daoItems.add(newItem)
        if (success != null)
            call.respond(HttpStatusCode.OK)
        else
            call.respond(HttpStatusCode.BadRequest)
    }
}