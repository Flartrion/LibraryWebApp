package security

import io.ktor.server.application.*
import io.ktor.server.auth.*


fun Application.configureSecurity() {

    install(Authentication) {
        configJWT(this@configureSecurity.environment.config)
        configOauthGithub(this@configureSecurity.environment.config)
    }
}