import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import tech.themukha.plugins.configurePlugins
import tech.themukha.plugins.configureStatic
import tech.themukha.plugins.configureApi
import tech.themukha.plugins.configureHomePage
import tech.themukha.utils.AppLogger.info

fun main(args: Array<String>) {
    info("Starting server on port 8080...")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configure()
    }.start(wait = true)
}

fun Application.configure() {
    info("Configuring application plugins and routes...")
    configurePlugins()
    configureStatic()
    configureApi()
    configureHomePage()
    info("Application configuration complete")
}
