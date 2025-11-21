package tech.themukha.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.img
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.section
import kotlinx.html.title
import kotlinx.html.ul
import tech.themukha.state.SiteState

fun Application.configureHomePage() {
    routing {
        // Main page
        get("/") {
            val content = SiteState.content

            call.respondHtml(HttpStatusCode.OK) {
                head {
                    meta { charset = "UTF-8" }
                    meta {
                        name = "viewport"
                        this.content = "width=device-width, initial-scale=1.0"
                    }
                    title { +"${content.fullName} — Personal Site" }
                    link(rel = "stylesheet", href = "/static/css/styles.css", type = "text/css")
                }
                body {
                    div(classes = "page") {
                        header(classes = "header") {
                            div(classes = "avatar-wrapper") {
                                img(src = content.photoUrl, alt = "Photo of ${content.fullName}") {
                                    classes = setOf("avatar")
                                }
                            }
                            div(classes = "intro") {
                                h1 { +content.fullName }
                                p(classes = "tagline") { +content.tagline }
                                p(classes = "about") { +content.about }
                            }
                        }

                        section(classes = "section") {
                            h2 { +"Contacts" }
                            ul(classes = "links-list") {
                                li {
                                    a(href = content.telegram, target = "_blank") {
                                        +"Telegram (@themukha)"
                                    }
                                }
                                li {
                                    a(href = content.whatsapp, target = "_blank") {
                                        +"WhatsApp (+66 63 885 3290)"
                                    }
                                }
                                li {
                                    a(href = content.email) {
                                        +"Email (george@themukha.tech)"
                                    }
                                }
                                li {
                                    a(href = content.linkedin, target = "_blank") {
                                        +"LinkedIn"
                                    }
                                }
                            }
                        }

                        section(classes = "section") {
                            h2 { +"Resumes" }
                            div(classes = "cv-links") {
                                a(href = content.cvEnUrl, target = "_blank", classes = "btn") {
                                    +"English CV"
                                }
                                a(href = content.cvRuUrl, target = "_blank", classes = "btn secondary") {
                                    +"Russian CV"
                                }
                            }
                        }

                        footer(classes = "footer") {
                            p {
                                +"© ${java.time.Year.now()} ${content.fullName}. themukha.tech"
                            }
                        }
                    }
                }
            }
        }
    }
}
