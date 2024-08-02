package routes.api.login

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import db.DatabaseSingleton
import db.entity.UserEntity
import db.model.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.plainJWTLogin(config: ApplicationConfig) {
    post("plain") {
        val parameters: Parameters
        try {
            parameters = call.receiveParameters()
        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest, e.message ?: "Login form parse failed!")
            return@post
        }

        // I'm ignoring the edge case of more than one return. That is not a situation that should arise.
        // Admin skill issue.
        val user = DatabaseSingleton.dbQuery {
            UserEntity.find {
                Users.email eq (parameters["email"] ?: "")
                Users.phoneNumber eq (parameters["password"] ?: "")
            }.firstOrNull()
        }

        if (user != null) {
            val audience = config.property("jwt.audience").getString()
            val issuer = config.property("jwt.issuer").getString()
            val secret = config.property("jwt.secret").getString()

            val token = JWT.create()
                .withSubject("TestAuthentication")
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.fullName)
                .withClaim("role", user.role)
                .withClaim("id", user.id.toString())
                .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 3600))
                .sign(Algorithm.HMAC256(secret))

            val cookieSettings = "path=/; " + "Max-Age=7200; " + "Secure; " + "SameSite=Strict"

            call.response.headers.append(
                HttpHeaders.SetCookie, "JWTAuth=${token}; " + "HttpOnly; " + cookieSettings
            )
            call.response.headers.append(
                HttpHeaders.SetCookie, "userID=${user.id}; " + cookieSettings
            )
            call.response.headers.append(
                HttpHeaders.SetCookie, "userName=${user.fullName}; " + cookieSettings
            )
            call.response.headers.append(
                HttpHeaders.SetCookie, "userRole=${user.role}; " + cookieSettings
            )


//            Removed due to being unsatisfactory in lack of SameSite option
//            call.response.cookies.append("userName", user.fullName, maxAge = 7200L, secure = true, path = "/")
//            call.response.cookies.append("userRole", user.role.toString(), maxAge = 7200L, secure = true, path = "/")
            call.respond(HttpStatusCode.OK, "Authorized!")
        } else call.respond(HttpStatusCode.Unauthorized, "Login failed")
    }
}