package routes

import DataBase.statement
import Items
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.itemsRouting() {
    route("/items") {
        get {
            var filter: String
            val data = call.request.queryParameters
//            val data = call.receiveParameters()
            println(data.entries().size)
            if (data.entries().size > 1) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (data["isbn"] != null)
                    filterConditions.add("isbn LIKE '%${data["isbn"]}%'")
                if (data["rlbc"] != null)
                    filterConditions.add("rlbc LIKE '%${data["rlbc"]}%'")
                if (data["title"] != null)
                    filterConditions.add("title LIKE '%${data["title"]}%'")
                if (data["authors"] != null)
                    filterConditions.add("authors LIKE '%${data["authors"]}%'")
                if (data["type"] != null)
                    filterConditions.add("type LIKE '%${data["type"]}%'")
                if (data["details"] != null)
                    filterConditions.add("details LIKE '%${data["details"]}%'")
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Items\"$filter" +
                            " ORDER BY authors ${data["ascDesc"]}, title ${data["ascDesc"]}"
                )
            val items = ArrayList<Items>()
            while (resultSet.next())
                items.add(
                    Items(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                    )
                )
            call.respondText(Json.encodeToString(items))
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Items\"" +
                            " WHERE id_item = $id"
                )
            val items = ArrayList<Items>()
            while (resultSet.next())
                items.add(
                    Items(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                    )
                )
            call.respondText(Json.encodeToString(items))
        }
        post("/insert") {
            val data = call.receiveParameters()
            val isbn = data["isbn"] ?: return@post call.respondText(
                "Missing or malformed isbn",
                status = HttpStatusCode.BadRequest
            )
            val rlbc = data["rlbc"] ?: return@post call.respondText(
                "Missing or malformed rlbc",
                status = HttpStatusCode.BadRequest
            )
            val title = data["title"] ?: return@post call.respondText(
                "Missing or malformed title",
                status = HttpStatusCode.BadRequest
            )
            val authors = data["authors"] ?: return@post call.respondText(
                "Missing or malformed authors",
                status = HttpStatusCode.BadRequest
            )
            val type = data["type"] ?: return@post call.respondText(
                "Missing or malformed type",
                status = HttpStatusCode.BadRequest
            )
            val details = data["details"] ?: return@post call.respondText(
                "Missing or malformed details",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Items\" (isbn, rlbc, title, authors, type, details)" +
                        " VALUES (\'$isbn\', \'$rlbc\', \'$title\', \'$authors\', \'$type\', \'$details\')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            var setExpression: String
            if (!call.receiveParameters().isEmpty()) {
                setExpression = " SET "
                val setParameters = ArrayList<String>()
                if (call.receiveParameters()["isbn"] != null)
                    setParameters.add("isbn = " + call.receiveParameters()["isbn"])
                if (call.receiveParameters()["rlbc"] != null)
                    setParameters.add("rlbc = " + call.receiveParameters()["rlbc"])
                if (call.receiveParameters()["title"] != null)
                    setParameters.add("title = " + call.receiveParameters()["title"])
                if (call.receiveParameters()["authors"] != null)
                    setParameters.add("authors = " + call.receiveParameters()["authors"])
                if (call.receiveParameters()["type"] != null)
                    setParameters.add("type = " + call.receiveParameters()["type"])
                if (call.receiveParameters()["details"] != null)
                    setParameters.add("details = " + call.receiveParameters()["details"])
                setExpression += setParameters[0]
                setParameters.removeAt(0)
                for (i in setParameters) {
                    setExpression += ", $i"
                }
            } else
                return@post call.respondText("Missing or malformed parameters", status = HttpStatusCode.BadRequest)
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"Items\"" +
                        setExpression +
                        " WHERE id_item = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Items\"" +
                        " WHERE id_item = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerItemsRoutes() {
    routing {
        itemsRouting()
    }
}