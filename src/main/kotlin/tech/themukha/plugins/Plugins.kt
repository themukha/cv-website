package tech.themukha.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import tech.themukha.utils.AppLogger.info

fun Application.configurePlugins() {
    info("Installing CallLogging and ContentNegotiation plugins...")
    install(CallLogging)
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }
    info("Plugins installed successfully.")
}
