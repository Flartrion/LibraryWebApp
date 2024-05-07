import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import db.DatabaseSingleton
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.http.parsing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import net.mamoe.yamlkt.toYamlElement
import routes.api.items.itemRoutes
import routes.api.storages.storagesRouting
import routes.api.users.userRoutes
import routes.loginRouting


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()
    val secret = environment.config.property("jwt.secret").getString()

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            authHeader {
                val JWTAuth = it.request.cookies["JWTAuth"]
                if (JWTAuth != null) {
                    try {
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
    DatabaseSingleton.init(config = environment.config)
    routing {
//        get("/") {
//            call.respondHtml(HttpStatusCode.OK, HTML::index)
//        }

        loginRouting(this@module.environment.config)

        storagesRouting()
        itemRoutes()
        userRoutes()

//        rentsRouting()
//        itemLocationRouting()
//        bankHistoryRouting()

        get("/favicon.ico") {
            val favicon = call.resolveResource("/favicon.ico", "static")
            if (favicon != null) {
                call.respond(favicon)
            }
        }

        staticResources("/", "static", index = "index.html") {

        }

    }
}

fun HTML.index() {
    head {
        title("In verbis virtus")
    }
    body {
        id = "root"
        script(src = "/static/index.js") {}
    }

}