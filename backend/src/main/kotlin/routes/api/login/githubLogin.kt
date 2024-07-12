package routes.api.login

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import security.UserSession
import security.applicationClient
import security.redirects

fun Route.githubLogin(config: ApplicationConfig) {
    authenticate("oauth-github") {
        route("github") {
            get {

            }
            get("callback") {
                println("Got to callback!")
                val code: String? =
                    call.request.queryParameters["code"]
                if (code == null) {
                    call.respond(HttpStatusCode.BadRequest, "Malformed code")
                    return@get
                }
                println("Got the code: $code")
                val state: String? =
                    call.request.queryParameters["state"]
                if (state == null) {
                    call.respond(HttpStatusCode.BadRequest, "Malformed state")
                    return@get
                }
                println("Got the state: $state")

                // Last I checked, this was empty despite redirect here properly occuring. I'll let it stew for a while.
                println(redirects)

                runBlocking {
                    val result = applicationClient.post("https://github.com/login/oauth/access_token") {
                        headers["Accept"] = "application/json"
                        setBody(
                            buildJsonObject {
                                put("client_id", config.property("github.clientid").getString())
                                put("client_secret", config.property("github.secret").getString())
                                put("code", code)
                            }
                        )
                    }
                    println(result.status)
                    println(result.bodyAsText())

//                val currentPrincipal: OAuthAccessTokenResponse.OAuth2? = call.principal()
//                // redirects home if the url is not found before authorization
//                currentPrincipal?.let { principal ->
//                    principal.state?.let { state ->
//                        call.sessions.set(UserSession(state, principal.accessToken))
//                        redirects[state]?.let { redirect ->
//                            call.respondRedirect(redirect)
//                            return@get
//                        }
//                    }
//                }
                }
                call.respondRedirect("/")
            }
        }
    }
}