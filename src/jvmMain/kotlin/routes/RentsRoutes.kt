package routes

import DataBase.statement
import Rents
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.rentsRouting() {
    route("/rents") {
        get {
            var filter: String
            if (!call.receiveParameters().isEmpty()) {
                filter = " WHERE "
                val filterConditions = ArrayList<String>()
                if (call.receiveParameters()["id_user"] != null)
                    filterConditions.add("id_user = " + call.receiveParameters()["id_user"])
                if (call.receiveParameters()["id_copy"] != null)
                    filterConditions.add("id_copy = " + call.receiveParameters()["id_copy"])
                if (call.receiveParameters()["from_date"] != null)
                    filterConditions.add("from_date = " + call.receiveParameters()["from_date"])
                if (call.receiveParameters()["until_date"] != null)
                    filterConditions.add("until_date = " + call.receiveParameters()["until_date"])
                filter += filterConditions[0]
                filterConditions.removeAt(0)
                for (i in filterConditions) {
                    filter += " AND $i"
                }
            } else
                filter = String()
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Rents\"$filter"
                )
            val rents = ArrayList<Rents>()
            while (resultSet.next())
                rents.add(
                    Rents(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                    )
                )
            call.respondText(Json.encodeToString(rents))
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val resultSet = statement
                .executeQuery(
                    "SELECT * FROM \"Inventory\".\"Rents\"" +
                            " WHERE id_rent = '$id'"
                )
            val rents = ArrayList<Rents>()
            while (resultSet.next())
                rents.add(
                    Rents(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                    )
                )
            call.respondText(Json.encodeToString(rents))
        }
        post("/insert") {
            val idUser = call.receiveParameters()["id_user"] ?: return@post call.respondText(
                "Missing or malformed id_user",
                status = HttpStatusCode.BadRequest
            )
            val idCopy = call.receiveParameters()["id_copy"] ?: return@post call.respondText(
                "Missing or malformed id_copy",
                status = HttpStatusCode.BadRequest
            )
            val fromDate = call.receiveParameters()["from_date"] ?: return@post call.respondText(
                "Missing or malformed from_date",
                status = HttpStatusCode.BadRequest
            )
            val untilDate = call.receiveParameters()["until_date"] ?: return@post call.respondText(
                "Missing or malformed until_date",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "INSERT INTO \"Inventory\".\"Rents\" (id_user, id_copy, from_date, until_date)" +
                        " VALUES ($idUser, $idCopy, $fromDate, $untilDate)"
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
                if (call.receiveParameters()["id_user"] != null)
                    setParameters.add("id_user = " + call.receiveParameters()["id_user"])
                if (call.receiveParameters()["id_copy"] != null)
                    setParameters.add("id_copy = " + call.receiveParameters()["id_copy"])
                if (call.receiveParameters()["from_date"] != null)
                    setParameters.add("from_date = " + call.receiveParameters()["from_date"])
                if (call.receiveParameters()["until_date"] != null)
                    setParameters.add("until_date = " + call.receiveParameters()["until_date"])
                setExpression += setParameters[0]
                setParameters.removeAt(0)
                for (i in setParameters) {
                    setExpression += ", $i"
                }
            } else
                return@post call.respondText("Missing or malformed parameters", status = HttpStatusCode.BadRequest)
            statement.executeUpdate(
                "UPDATE \"Inventory\".\"Rents\"" +
                        " SET id_user = '', id_copy = '', from_date = '', until_date = ''" +
                        " WHERE id_rent = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            statement.executeUpdate(
                "DELETE FROM \"Inventory\".\"Rents\"" +
                        " WHERE id_rent = '$id'"
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.registerRentsRoutes() {
    routing {
        rentsRouting()
    }
}