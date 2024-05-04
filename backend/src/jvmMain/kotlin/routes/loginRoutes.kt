package routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

fun Route.loginRouting(config: ApplicationConfig) {
    post("/login") {
        TODO("WIP")
        val parameters = call.receiveParameters()
        val username = parameters["username"] ?: return@post call.respondText(
            "Missing or malformed role",
            status = HttpStatusCode.BadRequest
        )
        val password = parameters["password"] ?: return@post call.respondText(
            "Missing or malformed role",
            status = HttpStatusCode.BadRequest
        )


        val publicKey = File(config.property("jwtkeys.public").getString()).readText()
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(publicKey))
        val privateKey = KeyFactory.getInstance("EC").generatePrivate(keySpecPKCS8)
        val token = JWT.create()
            .withAudience("audience")
            .withIssuer("issuer")
            .withClaim("username", "username")
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.ECDSA512("publicKey" as RSAPublicKey, "privateKey" as RSAPrivateKey))
    }
}