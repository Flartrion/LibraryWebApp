package security

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.config.*

val redirects = mutableMapOf<String, String>()

data class UserSession(val state: String, val token: String)

fun AuthenticationConfig.configOauthGithub(config: ApplicationConfig) {
    oauth("oauth-github") {
        urlProvider = { "https://localhost:8080/login/github/callback" }
        providerLookup = {
            OAuthServerSettings.OAuth2ServerSettings(
                name = "github",
                authorizeUrl = "https://github.com/login/oauth/authorize",
                accessTokenUrl = "https://github.com/login/oauth/access_token",
                requestMethod = HttpMethod.Post,
                clientId = config.property("github.clientid").getString(),
                clientSecret = config.property("github.secret").getString(),
//              Default scope of general user info. Enough for my needs.
                defaultScopes = listOf("user:mail, read:user"),
//              Don't see these described in github docs, so...
                extraAuthParameters = listOf("access_type" to "offline"),
                extraTokenParameters = listOf("accept" to "json"),
                onStateCreated = { call, state ->
                    //saves new state with redirect url value
                    call.request.queryParameters["redirectUrl"]?.let {
                        redirects[state] = it
                    }
                }
            )
        }
        client = applicationClient
    }
}