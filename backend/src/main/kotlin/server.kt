import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import db.DatabaseSingleton
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.html.*
import routes.api.items.itemRoutes
import routes.api.storages.storagesRouting
import routes.api.users.userRoutes
import routes.loginRouting


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    val issuer = environment.config.property("ktor.jwt.issuer").getString()
    val audience = environment.config.property("ktor.jwt.audience").getString()
    val myRealm = environment.config.property("ktor.jwt.realm").getString()
    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT.require(Algorithm.HMAC256(this@module.environment.config.property("ktor.jwt.secret").getString()))
                    .withAudience(audience).withIssuer(issuer).build()
            )
            validate { jwtCredential ->
                // I don't believe I particularly need this since the check is being performed at time of
                // issuing the JWT, but I will leave it here nonetheless, as a reminder.
                if (jwtCredential.payload.claims["username"].toString() != "")
                    JWTPrincipal(jwtCredential.payload)
                else
                    null
            }
        }
    }
    DatabaseSingleton.init(config = environment.config)
    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }

        loginRouting(environment!!.config)

        storagesRouting()
        itemRoutes()
        userRoutes()

//        rentsRouting()
//        itemLocationRouting()
//        bankHistoryRouting()

        staticResources("/static", basePackage = "/static") {

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