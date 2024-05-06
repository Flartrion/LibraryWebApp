package routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import db.DatabaseSingleton.dbQuery
import db.entity.UserEntity
import db.model.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.loginRouting(config: ApplicationConfig) {
    post("/login") {
        val parameters = call.receiveParameters()

        // I'm ignoring the edge case of more than one return. That is not a situation that should arise.
        // Admin skill issue.
        val user = dbQuery {
            UserEntity.find {
                Users.email like (parameters["email"] ?: "")
                Users.phoneNumber like (parameters["password"] ?: "")
            }.firstOrNull()
        }

        if (user != null) {
            val audience = config.property("ktor.jwt.audience").toString()
            val issuer = config.property("ktor.jwt.issuer").toString()
            val secret = config.property("ktor.jwt.secret").toString()

            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.fullName)
                .withClaim("role", user.role)
                .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 3600))
                .sign(Algorithm.HMAC256(secret))

            call.response.headers.append(
                HttpHeaders.SetCookie,
                "JWTAuth=${token}; " +
                        "Max-Age=3600000; " +
                        "Secure; " +
                        "HttpOnly; " +
                        "SameSite=Strict"
            )
            call.response.cookies.append("userName",user.fullName, maxAge = 3600000L, secure = true)
            call.response.cookies.append("userRole",user.role.toString(), maxAge = 3600000L, secure = true)
            call.respond(HttpStatusCode.OK, "Authorized!")
        } else
            call.respond(HttpStatusCode.Unauthorized, "Login failed")
    }
}