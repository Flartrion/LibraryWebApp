package routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import db.entity.UserEntity
import db.model.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun Route.loginRouting(config: ApplicationConfig) {
    post("/login") {
        val parameters = call.receiveParameters()
        val username = parameters["email"] ?: return@post call.respondText(
            "Missing or malformed email",
            status = HttpStatusCode.BadRequest
        )
        val password = parameters["password"] ?: return@post call.respondText(
            "Missing or malformed password",
            status = HttpStatusCode.BadRequest
        )

        // I'm ignoring the edge case of more than one return. That is not a situation that should arise.
        // Admin skill issue.
        val user = UserEntity.find {
            Users.email like username
            Users.phoneNumber like password
        }.firstOrNull()

        if (user != null) {
            val audience = config.property("ktor.jwk.audience").toString()
            val issuer = config.property("ktor.jwk.issuer").toString()
            val secret = config.property("ktor.jwk.secret").toString()

            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.fullName)
                .withClaim("role", user.role)
                .withExpiresAt(Date(System.currentTimeMillis() + 1000000))
                .sign(Algorithm.HMAC256(secret))
            call.respond(HttpStatusCode.OK, Json.encodeToString(mapOf("token" to token)))
        } else
            call.respond(HttpStatusCode.BadRequest, "Login failed")
    }
}