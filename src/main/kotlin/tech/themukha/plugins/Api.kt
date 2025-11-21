package tech.themukha.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import tech.themukha.state.SiteState

fun Application.configureApi() {
    routing {
        // Public API: get current content
        get("/api/content") {
            call.respond(SiteState.content)
        }

        // Admin API: update content, not yet implemented
        put("/api/content") {
            call.respond(HttpStatusCode.NotImplemented, "Feature not yet implemented")
//            // Simple admin token check via header
//            val adminToken = System.getenv("ADMIN_TOKEN") ?: "change-me"
//            val requestToken = call.request.headers["X-Admin-Token"]
//
//            if (requestToken == null || requestToken != adminToken) {
//                call.respond(HttpStatusCode.Unauthorized, "Invalid admin token")
//                return@put
//            }
//
//            // Parse JSON body into SiteContent and replace current state
//            val newContent = call.receive<SiteContent>()
//            SiteState.content = newContent
//            call.respond(HttpStatusCode.OK, newContent)
        }
    }
}
