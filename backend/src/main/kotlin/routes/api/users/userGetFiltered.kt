package routes.api.users

import dataType.User
import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import db.model.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import support.filterWrap

fun Route.userGetFiltered() {
    post("get") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.Unauthorized, "User ID failed")
            return@post
        }

        val role = principal.payload.getClaim("role").asInt()
        if (role >= 5) {
            call.respond(HttpStatusCode.Unauthorized, "Not enough privilege")
            return@post
        }

        try {
            val search = call.receive<User>()
            val searchFilter = search.copy(
                id = search.id,
                role = search.role,
                dob = search.dob,
                phoneNumber = filterWrap(search.phoneNumber),
                email = filterWrap(search.email),
                fullName = filterWrap(search.fullName)
            )

//            val resultSet = dbQuery {
//                val query = Users.selectAll()
//                searchFilter.role.toIntOrNull()?.let {
//                    query.andWhere {
//                        Users.role eq it
//                    }
//                }
//                searchFilter.dob?.let {
//                    query.andWhere {
//                        Users.dob eq it.toLocalDate()
//                    }
//                }
//                query.andWhere {
//                    (Users.email like searchFilter.email) and
//                            (Users.phoneNumber like searchFilter.phoneNumber) and
//                            (Users.fullName like searchFilter.fullName)
//                }
//                query.sortedByDescending { Users.email }.toList()
//            }

            val resultSet = dbQuery {
                UserEntity.find(Op.build {
                    (Users.email like searchFilter.email) and
                            (Users.fullName like searchFilter.fullName) and
                            (Users.phoneNumber like searchFilter.phoneNumber) and
                            (if (!searchFilter.dob.isNullOrEmpty()) (Users.dob eq searchFilter.dob.toLocalDate()) else Op.TRUE) and
                            (if (searchFilter.role.isNotEmpty()) (Users.role eq searchFilter.role.toInt()) else Op.TRUE)

                }).sortedByDescending { Users.email }
            }

            if (resultSet.isNotEmpty()) {
                call.respondText(
                    Json.encodeToString(resultSet.map { it.toDataclass() }),
                    ContentType.Application.Json,
                    HttpStatusCode.OK
                )
                return@post
            } else {
                call.respond(HttpStatusCode.NotFound, "No such users found in database")
                return@post
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Unknown error")
            return@post
        }
    }
}