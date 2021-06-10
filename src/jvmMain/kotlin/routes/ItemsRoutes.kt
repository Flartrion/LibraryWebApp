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
            if (call.request.cookies["role"] != "admin" || call.request.cookies["role"] != "user") return@get call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            var filter: String
            val parameters = call.request.queryParameters
            if (parameters.entries().size > 1) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (parameters["isbn"] != null)
                    filterConditions.add("isbn LIKE '%${parameters["isbn"]}%'")
                if (parameters["rlbc"] != null)
                    filterConditions.add("rlbc LIKE '%${parameters["rlbc"]}%'")
                if (parameters["title"] != null)
                    filterConditions.add("title LIKE '%${parameters["title"]}%'")
                if (parameters["authors"] != null)
                    filterConditions.add("authors LIKE '%${parameters["authors"]}%'")
                if (parameters["type"] != null)
                    filterConditions.add("type LIKE '%${parameters["type"]}%'")
                if (parameters["details"] != null)
                    filterConditions.add("details LIKE '%${parameters["details"]}%'")
                if (parameters["language"] != null)
                    filterConditions.add("language LIKE '%${parameters["language"]}%'")
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
                            " ORDER BY authors ${parameters["ascDesc"]}, title ${parameters["ascDesc"]}"
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
                        resultSet.getString(7),
                        resultSet.getString(8)
                    )
                )
            call.respondText(Json.encodeToString(items))
        }
        get("{id}") {
            if (call.request.cookies["role"] != "admin" || call.request.cookies["role"] != "user") return@get call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
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
                        resultSet.getString(7),
                        resultSet.getString(8)
                    )
                )
            call.respondText(Json.encodeToString(items))
        }
        post("/insert") {
            if (call.request.cookies["role"] != "admin") return@post call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val parameters = call.receiveParameters()
            val isbn = parameters["isbn"] ?: return@post call.respondText(
                "Missing or malformed isbn",
                status = HttpStatusCode.BadRequest
            )
            val rlbc = parameters["rlbc"] ?: return@post call.respondText(
                "Missing or malformed rlbc",
                status = HttpStatusCode.BadRequest
            )
            val title = parameters["title"] ?: return@post call.respondText(
                "Missing or malformed title",
                status = HttpStatusCode.BadRequest
            )
            val authors = parameters["authors"] ?: return@post call.respondText(
                "Missing or malformed authors",
                status = HttpStatusCode.BadRequest
            )
            val type = parameters["type"] ?: return@post call.respondText(
                "Missing or malformed type",
                status = HttpStatusCode.BadRequest
            )
            val details = parameters["details"] ?: return@post call.respondText(
                "Missing or malformed details",
                status = HttpStatusCode.BadRequest
            )
            val language = parameters["language"] ?: return@post call.respondText(
                "Missing or malformed language",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Items\" (isbn, rlbc, title, authors, type, details, language)" +
                        " VALUES ('$isbn', '$rlbc', '$title', '$authors', '$type', '$details', '$language')"
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/update/{id}") {
            if (call.request.cookies["role"] != "admin") return@post call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            var setExpression: String
            val parameters = call.receiveParameters()
            if (!parameters.isEmpty()) {
                setExpression = " SET "
                val setParameters = ArrayList<String>()
                if (parameters["isbn"] != null)
                    setParameters.add("isbn = '" + parameters["isbn"] + "'")
                if (parameters["rlbc"] != null)
                    setParameters.add("rlbc = '" + parameters["rlbc"] + "'")
                if (parameters["title"] != null)
                    setParameters.add("title = '" + parameters["title"] + "'")
                if (parameters["authors"] != null)
                    setParameters.add("authors = '" + parameters["authors"] + "'")
                if (parameters["type"] != null)
                    setParameters.add("type = '" + parameters["type"] + "'")
                if (parameters["details"] != null)
                    setParameters.add("details = '" + parameters["details"] + "'")
                if (parameters["language"] != null)
                    setParameters.add("language = '" + parameters["language"] + "'")
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
            if (call.request.cookies["role"] != "admin") return@delete call.respondText(
                "Access is forbidden",
                status = HttpStatusCode.Forbidden
            )
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