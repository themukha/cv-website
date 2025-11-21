package tech.themukha.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.FlowContent
import kotlinx.html.HEAD
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.h4
import kotlinx.html.h5
import kotlinx.html.h6
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.nav
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.span
import kotlinx.html.i
import kotlinx.html.ul
import kotlinx.html.li
import kotlinx.html.style
import kotlinx.html.title
import kotlinx.html.unsafe
import tech.themukha.models.NavItem
import tech.themukha.models.SiteContent
import tech.themukha.models.StackItem
import tech.themukha.state.SiteState
import java.time.LocalDateTime

fun Application.configureHomePage() {
    routing {
        get("/") {
            val content = SiteState.content

            call.respondHtml(HttpStatusCode.OK) {
                head {
                    renderHead(content)
                }

                body {
                    attributes["class"] = "bg-slate-950 text-slate-200 antialiased selection:bg-indigo-500 selection:text-white"

                    // --- NAVIGATION ---
                    run {
                        renderNav(content.navigation)
                    }

                    // --- HERO SECTION ---
                    renderHero(content)

                    // --- STACK SECTION ---
                    run {
                        renderStack(content.commonStack)
                    }

                    // --- RESUME SECTION ---
                    renderResume(content)

                    // --- CONTACT SECTION ---
                    renderContacts(content)

                    // --- FOOTER ---
                    renderFooter(content)

                    // --- SCRIPTS ---
                    renderScripts()
                }
            }
        }
    }
}

// --- Helpers (builders) ---
private fun HEAD.renderHead(content: SiteContent) {
    meta { charset = "UTF-8" }
    meta {
        name = "viewport"
        this.content = "width=device-width, initial-scale=1.0"
    }
    title { +"${content.fullName} | ${content.tagline}" }

    script { src = "https://cdn.tailwindcss.com" }

    link(rel = "stylesheet", href = "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0")
    link(href = "https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;700&family=Inter:wght@300;400;600;800&display=swap", rel = "stylesheet")
    link(rel = "stylesheet", href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css")

    style {
        unsafe {
            raw(
                """
                body { font-family: 'Inter', sans-serif; }
                .font-mono { font-family: 'JetBrains Mono', monospace; }
                .glass-panel {
                    background: rgba(17, 24, 39, 0.7);
                    backdrop-filter: blur(12px);
                    -webkit-backdrop-filter: blur(12px);
                    border: 1px solid rgba(255, 255, 255, 0.08);
                }
                .gradient-text {
                    background: linear-gradient(to right, #818cf8, #c084fc);
                    -webkit-background-clip: text;
                    -webkit-text-fill-color: transparent;
                }
                ::-webkit-scrollbar { width: 8px; }
                ::-webkit-scrollbar-track { background: #111827; }
                ::-webkit-scrollbar-thumb { background: #374151; border-radius: 4px; }
                ::-webkit-scrollbar-thumb:hover { background: #4b5563; }
                .animate-fade-in { animation: fadeIn 0.6s ease-out forwards; opacity: 0; transform: translateY(20px); }
                @keyframes fadeIn { to { opacity: 1; transform: translateY(0); } }
                """
            )
        }
    }
}

private fun FlowContent.renderNav(items: List<NavItem>) {
    nav {
        attributes["class"] = "fixed top-0 w-full z-50 glass-panel border-b border-slate-800"
        div("max-w-6xl mx-auto px-4 sm:px-6 lg:px-8") {
            div("flex justify-between h-16 items-center") {
                // Logo
                div("flex-shrink-0 flex items-center gap-2 cursor-pointer") {
                    onClick = "window.scrollTo({top: 0, behavior: 'smooth'})"
                    span("material-symbols-outlined text-indigo-400") { +"terminal" }
                    span("font-mono font-bold text-xl tracking-tighter") { +"themukha.tech" }
                }
                // Desktop Menu
                div("hidden md:flex space-x-8 items-center") {
                    items.forEach { a(href = it.href, classes = "text-sm font-medium hover:text-indigo-400 transition-colors") { +it.text } }
                }
                // Mobile Menu Button (Icon)
                div("md:hidden flex items-center") {
                    button(classes = "text-slate-300 hover:text-white") {
                        id = "mobile-menu-btn"
                        span("material-symbols-outlined") { +"menu" }
                    }
                }
            }
        }
        // Mobile Menu Dropdown
        div("hidden md:hidden bg-slate-900 border-b border-slate-800") {
            id = "mobile-menu"
            div("px-4 pt-2 pb-4 space-y-1") {
                items.forEach { a(href = it.href, classes = "block px-3 py-2 rounded-md text-base font-medium hover:bg-slate-800") { +it.text } }
            }
        }
    }
}

private fun FlowContent.renderHero(content: SiteContent) {
    section {
        id = "about"
        attributes["class"] = "relative pt-32 pb-20 lg:pt-48 lg:pb-32 overflow-hidden"

        // Background Elements
        div("absolute top-0 left-0 w-full h-full overflow-hidden -z-10") {
            div("absolute top-[-10%] right-[-5%] w-[500px] h-[500px] bg-indigo-600/20 rounded-full blur-[120px]") {}
            div("absolute bottom-[-10%] left-[-10%] w-[600px] h-[600px] bg-purple-600/10 rounded-full blur-[100px]") {}
        }

        div("max-w-6xl mx-auto px-4 sm:px-6 lg:px-8") {
            div("grid grid-cols-1 lg:grid-cols-2 gap-12 items-center") {
                // Text Content
                div("space-y-8 animate-fade-in") {
                    attributes["style"] = "animation-delay: 0.1s;"
                    div {
                        div("inline-flex items-center space-x-2 px-3 py-1 rounded-full bg-indigo-500/10 border border-indigo-500/20 text-indigo-300 text-sm font-medium mb-6") {
                            span("relative flex h-2 w-2") {
                                span("animate-ping absolute inline-flex h-full w-full rounded-full bg-indigo-400 opacity-75") {}
                                span("relative inline-flex rounded-full h-2 w-2 bg-indigo-500") {}
                            }
                            span { +"Open to offers" }
                        }
                        h1("text-5xl lg:text-7xl font-bold tracking-tight text-white mb-4") {
                            +"George "
                            span("gradient-text") { +"Mukha" }
                        }
                        p("text-xl text-slate-400 font-light max-w-lg leading-relaxed") {
                            unsafe { +content.about }
                        }
                    }
                    div("flex flex-wrap gap-4") {
                        a(href = content.telegram, classes = "px-8 py-4 bg-indigo-600 hover:bg-indigo-500 text-white font-semibold rounded-xl transition-all shadow-lg shadow-indigo-500/25 flex items-center gap-2 group") {
                            span { +"Contact with me" }
                            span("material-symbols-outlined group-hover:translate-x-1 transition-transform") { +"arrow_forward" }
                        }
                        a(href = "#resume", classes = "px-8 py-4 bg-slate-800 hover:bg-slate-700 text-white font-semibold rounded-xl transition-all border border-slate-700 flex items-center gap-2") {
                            span("material-symbols-outlined") { +"download" }
                            span { +"Download CV" }
                        }
                    }
                }

                // Hero Visual (Avatar)
                div("relative animate-fade-in lg:h-[600px] flex items-center justify-center") {
                    attributes["style"] = "animation-delay: 0.3s;"
                    div("relative w-full aspect-square max-w-[500px] mx-auto") {
                        div("absolute inset-0 border-2 border-slate-700/50 rounded-full rotate-12 scale-105") {}
                        div("absolute inset-0 border border-indigo-500/30 rounded-full -rotate-6 scale-110 border-dashed") {}

                        // Image
                        div("absolute inset-4 rounded-full overflow-hidden ring-4 ring-slate-800 shadow-2xl") {
                            img(src = content.photoUrl, alt = content.fullName, classes = "w-full h-full object-cover hover:scale-105 transition-transform duration-700")
                        }

                        // Floating Stats
                        div("absolute -right-4 top-20 glass-panel p-4 rounded-2xl border border-slate-700 shadow-xl animate-bounce") {
                            attributes["style"] = "animation-duration: 3s;"
                            div("flex items-center gap-3") {
                                div("p-2 bg-orange-500/10 rounded-lg") { img(src = "https://cdn.simpleicons.org/kotlin", alt = "Kotlin", classes = "w-10 h-10 inline-block group-hover:scale-110 transition-transform") }
                                div {
                                    p("text-xs text-slate-400") { +"Main Language" }
                                    p("font-bold text-white") { +"Kotlin Expert" }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun FlowContent.renderStack(items: List<StackItem>) {
    section {
        id = "stack"
        attributes["class"] = "py-20 bg-slate-900/50 border-y border-slate-800"
        div("max-w-6xl mx-auto px-4 sm:px-6 lg:px-8") {
            h2("text-3xl font-bold text-white mb-12 text-center") { +"Technology Stack" }
            div("grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-6") {
                fun FlowContent.techCard(iconUrl: String, title: String, subtitle: String) {
                    div("p-6 bg-slate-800/50 rounded-xl border border-slate-700 hover:border-indigo-500/50 transition-all text-center group") {
                        div("mb-3 flex justify-center") { img(src = iconUrl, alt = title, classes = "w-10 h-10 inline-block group-hover:scale-110 transition-transform") }
                        h3("font-bold text-slate-200") { +title }
                        p("text-xs text-slate-400 mt-1") { +subtitle }
                    }
                }
                items.forEach { techCard(it.iconUrl, it.title, it.subtitle) }
                div("mt-6 col-span-full text-center") {
                    p("text-xs text-slate-400 italic") { +"More details in the resume below..." }
                }
            }
        }
    }
}

private fun FlowContent.renderResume(content: SiteContent) {
    section {
        id = "resume"
        attributes["class"] = "py-24"
        div("max-w-4xl mx-auto px-4 sm:px-6 lg:px-8") {
            div("text-center mb-8") {
                h2("text-3xl font-bold text-white mb-4") { +"My CV" }
                p("text-slate-400") { +"Available in two languages (EN/RU)" }
            }

            // Download Buttons
            div("flex justify-center gap-4 mb-8") {
                a(href = content.cvRuUrl, target = "_blank", classes = "flex items-center gap-2 px-5 py-2.5 bg-slate-800 hover:bg-slate-700 rounded-lg text-slate-200 transition-colors border border-slate-700") {
                    span("material-symbols-outlined") { +"download" }
                    +"CV (RU)"
                }
                a(href = content.cvEnUrl, target = "_blank", classes = "flex items-center gap-2 px-5 py-2.5 bg-slate-800 hover:bg-slate-700 rounded-lg text-slate-200 transition-colors border border-slate-700") {
                    span("material-symbols-outlined") { +"download" }
                    +"CV (EN)"
                }
            }

            // Resume Preview Card
            div("glass-panel rounded-2xl p-1 border border-slate-700 shadow-2xl") {
                // Toolbar
                div("bg-slate-800/80 rounded-t-xl px-4 py-3 flex justify-between items-center border-b border-slate-700") {
                    div("flex space-x-2") {
                        div("w-3 h-3 rounded-full bg-red-500") {}
                        div("w-3 h-3 rounded-full bg-yellow-500") {}
                        div("w-3 h-3 rounded-full bg-green-500") {}
                    }
                }
                // Content
                div("bg-white text-slate-800 p-8 md:p-12 min-h-[400px] rounded-b-xl relative overflow-hidden") {
                    div("absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-slate-100 text-9xl font-bold -rotate-45 pointer-events-none select-none") { +"CV" }

                    div("animate-fade-in space-y-6") {
                        div("border-b border-gray-200 pb-4") {
                            h3("text-2xl font-bold text-slate-900") { +content.fullName }
                            p("text-indigo-600 font-medium") { +content.tagline }
                        }
                        div {
                            h4("font-bold text-slate-800 uppercase text-sm tracking-wider mb-2") { +"Experience" }
                            content.experiences.forEach { exp ->
                                div("mb-6") {
                                    // Company badge
                                    if (exp.company.isNotBlank()) {
                                        div("flex justify-between items-baseline") { h5("font-bold text-slate-700") { +exp.company } }
                                    }
                                    // Header: Role and Period
                                    div("flex justify-between items-baseline") {
                                        h6("font-bold text-slate-500") { +exp.role }
                                        span("text-sm text-slate-500") { +exp.period }
                                    }
                                    // About position
                                    if (exp.aboutPosition.isNotBlank()) { p("text-sm text-slate-600 mt-2") { +exp.aboutPosition } }
                                    // Responsibilities
                                    if (exp.responsibilities?.isNotEmpty() == true) {
                                        h6("mt-3 text-xs font-bold uppercase tracking-wider text-slate-500") { +"Responsibilities" }
                                        ul("list-disc list-inside text-sm text-slate-600 space-y-1 mt-1") { exp.responsibilities.forEach { item -> li { +item } } }
                                    }
                                    // Results
                                    if (exp.results.isNotEmpty()) {
                                        h6("mt-3 text-xs font-bold uppercase tracking-wider text-slate-500") { +"Results" }
                                        ul("list-disc list-inside text-sm text-slate-600 space-y-1 mt-1") { exp.results.forEach { item -> li { +item } } }
                                    }
                                    // Stack chips
                                    if (exp.stack.isNotEmpty()) {
                                        div("mt-3 flex flex-wrap gap-2") { exp.stack.forEach { tech -> span("px-2 py-0.5 rounded-full border border-slate-300 bg-slate-100 text-slate-700 text-xs") { +tech } } }
                                    }
                                }
                            }
                        }
                        div {
                            if (content.projectActivities.isNotEmpty()) {
                                div("my-6 border-t border-gray-200") {}
                                h4("font-bold text-slate-800 uppercase text-sm tracking-wider mb-2") { +"Project Activities" }
                                content.projectActivities.forEach { exp ->
                                    div("mb-6") {
                                        if (exp.company.isNotBlank()) {
                                            div("flex justify-between items-baseline") { h5("font-bold text-slate-700") { +exp.company } }
                                        }
                                        div("flex justify-between items-baseline") {
                                            h6("font-bold text-slate-500") { +exp.role }
                                            span("text-sm text-slate-500") { +exp.period }
                                        }
                                        if (exp.aboutPosition.isNotBlank()) { p("text-sm text-slate-600 mt-2") { +exp.aboutPosition } }
                                        if (exp.responsibilities?.isNotEmpty() == true) {
                                            h6("mt-3 text-xs font-bold uppercase tracking-wider text-slate-500") { +"Responsibilities" }
                                            ul("list-disc list-inside text-sm text-slate-600 space-y-1 mt-1") { exp.responsibilities.forEach { item -> li { +item } } }
                                        }
                                        if (exp.results.isNotEmpty()) {
                                            h6("mt-3 text-xs font-bold uppercase tracking-wider text-slate-500") { +"Results" }
                                            ul("list-disc list-inside text-sm text-slate-600 space-y-1 mt-1") { exp.results.forEach { item -> li { +item } } }
                                        }
                                        if (exp.stack.isNotEmpty()) {
                                            div("mt-3 flex flex-wrap gap-2") { exp.stack.forEach { tech -> span("px-2 py-0.5 rounded-full border border-slate-300 bg-slate-100 text-slate-700 text-xs") { +tech } } }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun FlowContent.renderContacts(content: SiteContent) {
    section {
        id = "contact"
        attributes["class"] = "py-24 bg-slate-900 relative overflow-hidden"
        div("absolute right-0 bottom-0 w-1/3 h-full bg-gradient-to-l from-indigo-900/20 to-transparent pointer-events-none") {}

        div("max-w-6xl mx-auto px-4 sm:px-6 lg:px-8") {
            div("grid grid-cols-1 lg:grid-cols-2 gap-16") {
                // Text & Links
                div {
                    h2("text-4xl font-bold text-white mb-6") { +"Let's work together" }
                    p("text-slate-400 mb-8 text-lg leading-relaxed") { +"I am always open to discussing new projects, ideas or opportunities." }

                    div("space-y-4") {
                        content.contacts.forEach { c ->
                            a(href = c.href, target = if (c.label == "Email") null else "_blank", classes = "flex items-center gap-4 p-4 rounded-xl bg-slate-800/50 border border-slate-700 hover:bg-slate-800 transition-all group") {
                                div(c.containerClasses) { i(c.iconClass) {} }
                                div {
                                    p("text-xs text-slate-500 uppercase font-bold tracking-wider") { +c.label }
                                    p("text-white font-mono") { +c.value }
                                }
                            }
                        }
                    }
                }

                // Map Visual
                div("relative h-full min-h-[400px] bg-slate-800 rounded-2xl overflow-hidden border border-slate-700") {
                    img(src = "/static/img/map.jpg", alt = "Location", classes = "w-full h-full object-cover opacity-60")
                    div("absolute inset-0 flex items-center justify-center p-6") {
                        div("bg-slate-900/90 backdrop-blur-md p-6 rounded-xl border border-slate-600 max-w-sm text-center shadow-2xl") {
                            span("material-symbols-outlined text-4xl text-indigo-400 mb-2") { +"flight_takeoff" }
                            h3("text-xl font-bold text-white") { +"Currently In" }
                            p("text-slate-300 mt-1") { +"Thailand / Remote" }
                            p("text-xs text-slate-500 mt-4") { +"Available for global relocation" }
                        }
                    }
                }
            }
        }
    }
}

private fun FlowContent.renderFooter(content: SiteContent) {
    footer("py-8 border-t border-slate-800 bg-slate-950 text-center") {
        p("text-slate-500 text-sm font-mono") { +"© ${LocalDateTime.now().year} ${content.fullName}, themukha.tech. Powered by Ktor" }
    }
}

private fun FlowContent.renderScripts() {
    script {
        unsafe {
            raw(
                """
                document.getElementById('mobile-menu-btn').addEventListener('click', () => {
                    const menu = document.getElementById('mobile-menu');
                    menu.classList.toggle('hidden');
                });
                """
            )
        }
    }
}
