package routes.api.items

import Item
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.itemGetAll() {
    get {
        var filter: String
        val parameters = call.request.queryParameters
        val itemParams = call.receive<Item>()
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
        val items = ArrayList<Item>()
        while (resultSet.next())
            items.add(
                Item(
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
}