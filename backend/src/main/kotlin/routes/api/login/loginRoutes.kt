package routes.api.login

import io.ktor.server.config.*
import io.ktor.server.routing.*

fun Route.loginRouting(config: ApplicationConfig) {
    route("login") {
        plainJWTLogin(config)
        githubLogin(config)
    }
}