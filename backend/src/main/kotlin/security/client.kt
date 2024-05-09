package security

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*


val applicationClient = HttpClient(CIO) {
    install(
        ContentNegotiation
    ) {
        json()
    }
}

fun Application.main(httpClient: HttpClient = applicationClient) {

}
