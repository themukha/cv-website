package tech.themukha.plugins

import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import tech.themukha.state.SiteState
import tech.themukha.utils.SeoUtils

fun Application.configureStatic() {
    routing {
        staticResources("/static", "static")

        get("/robots.txt") {
            call.respondText(SeoUtils.buildRobotsTxt(SiteState.content), ContentType.Text.Plain)
        }

        get("/sitemap.xml") {
            call.respondText(SeoUtils.buildSitemapXml(SiteState.content), ContentType.Text.Xml)
        }

        get("/llms.txt") {
            call.respondText(SeoUtils.buildLlmsTxt(SiteState.content), ContentType.Text.Plain)
        }

        get("/llms-full.txt") {
            call.respondText(SeoUtils.buildLlmsFullTxt(SiteState.content), ContentType.Text.Plain)
        }
    }
}
