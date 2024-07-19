package security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.engine.*
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.http.parsing.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.response.*

fun AuthenticationConfig.configJWT(config: ApplicationConfig) {
    val issuer = config.property("jwt.issuer").getString()
    val audience = config.property("jwt.audience").getString()
    val myRealm = config.property("jwt.realm").getString()
    val secret = config.property("jwt.secret").getString()

    jwt("auth-jwt") {
        realm = myRealm
        authHeader {
            val JWTAuth = it.request.cookies["JWTAuth"]
//            println(JWTAuth)
            if (JWTAuth != null) {
                try {
                    // TODO: Automatize auth scheme?
                    parseAuthorizationHeader("Bearer $JWTAuth")
                } catch (cause: ParseException) {
                    println("${cause.javaClass}: " + cause.message)
                    null
                }
            } else null
        }
        verifier(
            JWT.require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .acceptLeeway(5L).build()
        )
        validate { jwtCredential ->
            // I don't believe I particularly need this since the check is being performed at time of
            // issuing the JWT, but I will leave it here nonetheless, as a reminder.
//            println(jwtCredential.payload.claims)
            if (jwtCredential.payload.claims["username"].toString() != "") {
                JWTPrincipal(jwtCredential.payload)
            } else {
                null
            }
        }
        challenge { defaultScheme, realm ->
            println(call.authentication.allFailures.map { it.javaClass })
            call.respond(HttpStatusCode.Unauthorized, "No access to $realm")
        }
    }
}