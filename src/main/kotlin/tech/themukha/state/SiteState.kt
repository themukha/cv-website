package tech.themukha.state

import tech.themukha.models.ContactItem
import tech.themukha.models.Experience
import tech.themukha.models.NavItem
import tech.themukha.models.SiteContent
import tech.themukha.models.StackItem

object SiteState {
    // Base contact sources
    private const val DOMAIN = "themukha.tech"
    private const val FULL_NAME = "George Mukha"
    private const val TELEGRAM_URL = "https://t.me/themukha_tech"
    private const val LINKEDIN_URL = "https://www.linkedin.com/in/themukha/"
    private const val WHATSAPP_URL = "https://wa.me/66638853290"
    private const val EMAIL_ADDR = "george@themukha.tech"
    private const val GITHUB_URL = "https://github.com/themukha"

    private fun telegramHandle(url: String): String = url.substringAfterLast('/').let { if (it.isNotBlank()) "@${it}" else "Telegram" }
    private fun whatsAppDisplay(url: String): String = url.substringAfterLast('/').let { if (it.all { ch -> ch.isDigit() }) "+$it" else url }

    var content: SiteContent = SiteContent(
        navigation = listOf(
            NavItem("About me", "#about"),
            NavItem("Stack", "#stack"),
            NavItem("CV", "#resume"),
            NavItem("Contacts", "#contact")
        ),
        fullName = FULL_NAME,
        domain = DOMAIN,
        tagline = "QA Auto Engineer / SDET",
        about = "AQA/SDET with over 5 years of experience in the tech industry. I implement new technologies to automate product quality assurance.",
        telegram = TELEGRAM_URL,
        whatsapp = WHATSAPP_URL,
        email = EMAIL_ADDR,
        linkedin = LINKEDIN_URL,
        github = GITHUB_URL,
        photoUrl = "/static/img/avatar.jpg",
        cvEnUrl = "/static/cv_en.pdf",
        cvRuUrl = "/static/cv_ru.pdf",
        commonStack = listOf(
            StackItem("https://cdn.simpleicons.org/kotlin", "Kotlin", "Programming Language"),
            StackItem("/static/img/icons/java.svg", "Java", "Programming Language"),
            StackItem("https://cdn.simpleicons.org/postgresql", "PostgreSQL", "Database"),
            StackItem("https://cdn.simpleicons.org/clickhouse", "ClickHouse", "Database"),
            StackItem("/static/img/icons/kafka.svg", "Kafka", "Message Broker"),
            StackItem("/static/img/icons/fix.svg", "FIX", "Protocol"),
            StackItem("/static/img/icons/grpc.svg", "gRPC", "Protocol"),
            StackItem("/static/img/icons/websocket.svg", "WebSocket", "Protocol"),
            StackItem("https://cdn.simpleicons.org/openapiinitiative", "REST API", "Protocol"),
            StackItem("https://cdn.simpleicons.org/grafana", "Grafana", "Monitoring"),
            StackItem("https://cdn.simpleicons.org/docker", "Docker", "CI/CD"),
            StackItem("https://cdn.simpleicons.org/jenkins", "Jenkins", "CI/CD"),
        ),
        experiences = listOf(
            Experience(
                company = "T-Bank (50 mln clients)",
                role = "Senior QA Backend Engineer/QA Tech Leader",
                period = "July 2023 - October 2025",
                aboutPosition = "Development and implementation of all testing processes within a product consisting of 6 microservices handling financial calculations. Concurrently, I held the position of QA profession leader, where my responsibilities extended to technical practices across the entire company's QA team.",
                responsibilities = listOf(
                    "Development and implementation of functional and non-functional test scenarios",
                    "Implementation and expansion of load testing to product teams",
                    "Implementation of product processes and technical practices within the development department",
                    "Development of an automated QA AI service/Development of a QA Backend service",
                    "Release approval"
                ),
                results = listOf(
                    "Developed a QA AI service focused on writing test case scenarios, which reduced testing time from 1 week or more",
                    "Implemented API coverage from zero to 98%, which resulted in a reduction in production errors and a 12-fold reduction in the company's regulatory fine costs",
                    "Implemented load testing, which enabled the timely detection of over 500-times increases in request execution time and prevented SLA misses",
                    "Automated smoke test launches based on triggers in development repositories, reducing the development team's dependence on the testing team",
                    "Expanded the practice of load testing within the department, enabling us to understand during the development phase whether the product is capable of meeting non-functional requirements and fixing issues before production",
                    "Implemented the 3 Amigo product practice, which reduced the number of acceptance bugs by a factor of 4"
                ),
                stack = listOf(
                    "Java", "Kotlin", "Kafka", "gRPC", "WebSocket", "FIX", "REST API", "Grafana", "PostgreSQL", "LLM (model gemma-3-27b)", "GitLab CI/CD", "Jenkins", "Docker"
                )
            ),
            Experience(
                company = "WEDEVX.CO",
                role = "QA/AQA Engineer",
                period = "October 2022 - July 2023",
                aboutPosition = "Launching all testing processes from scratch (manual and automated) within an edtech product, specifically an asynchronous educational platform. QA team: 3 people.",
                responsibilities = listOf(
                    "Developing and implementing functional test scenarios (UI, API)",
                    "Creating a testing framework from scratch (Kotlin/Java)",
                    "Integrating testing processes into development stages",
                    "Approving releases"
                ),
                results = listOf(
                    "Building testing processes from scratch, improving the quality of the entire product and user satisfaction",
                    "Implementing API coverage with automated tests from zero to 100%, reducing testing time by tens of times compared to manual testing",
                    "Developing WEB UI automated tests for mobile devices, tablets, and personal computers, which also reduced testing time by tens of times compared to manual testing",
                    "Led the onboarding and training of two QA trainees, resulting in each QA being promoted to junior level"
                ),
                stack = listOf(
                    "Kotlin", "Java", "WebSocket", "REST API", "Grafana", "PostgreSQL", "MongoDB", "Jenkins", "Docker"
                )
            ),
            Experience(
                company = "Discovery Studio",
                role = "Fullstack Quality Assurance",
                period = "November 2020 - October 2022",
                aboutPosition = "Testing (manual and automated) of a mobile banking app.",
                responsibilities = listOf(
                    "Conducting regression testing of chat support and notifications",
                    "Writing automated tests (JavaScript, TypeScript)",
                    "Assigning element identifiers to Android (Kotlin) and iOS (Swift) repositories"
                ),
                results = listOf(
                    "Successfully maintained the quality of changes to iOS and Android mobile app products within the \"chat support\" and \"notifications\" sections"
                ),
                stack = listOf(
                    "Kotlin", "Swift", "JavaScript", "TypeScript", "WebSocket", "REST API", "ClickHouse"
                )
            ),
        ),
        projectActivities = listOf(
            Experience(
                company = "BHFT",
                role = "SDET",
                period = "November 2024 - March 2025",
                aboutPosition = "Development and deployment of QA backend services, ensuring the quality of the platform for high-frequency trading (HFT) in cryptocurrencies and traditional finance.",
                responsibilities = null,
                results = listOf(
                    "Developed a proxy service to intercept incoming and outgoing messages via FIX, gRPC, WebSocket, and HTTP protocols from the trading platform to trading exchanges. The service can modify requests/responses, simulate delays and channel blocking, and simultaneously work with multiple exchanges",
                    "Implemented a service for creating a queue for automated tests to interact with specific exchanges to avoid cross-trading",
                    "Developed and stabilized automated tests for market data gateways, execution gateways, and trading robots"
                ),
                stack = listOf(
                    "Kotlin", "Java", "Python", "REST API", "gRPC", "WebSocket", "FIX", "Gradle", "Docker", "Jenkins", "JUnit5", "ClickHouse", "PostgreSQL", "Ktor"
                )
            )
        ),
        contacts = listOf(
            ContactItem(
                label = "Telegram",
                href = TELEGRAM_URL,
                iconClass = "fa-brands fa-telegram",
                containerClasses = "p-3 bg-sky-500/20 rounded-lg text-sky-400 group-hover:bg-sky-500 group-hover:text-white transition-colors",
                value = telegramHandle(TELEGRAM_URL)
            ),
            ContactItem(
                label = "LinkedIn",
                href = LINKEDIN_URL,
                iconClass = "fa-brands fa-linkedin",
                containerClasses = "p-3 bg-emerald-500/20 rounded-lg text-emerald-400 group-hover:bg-emerald-500 group-hover:text-white transition-colors",
                value = FULL_NAME
            ),
            ContactItem(
                label = "Email",
                href = "mailto:$EMAIL_ADDR",
                iconClass = "fa-solid fa-envelope",
                containerClasses = "p-3 bg-indigo-500/20 rounded-lg text-indigo-400 group-hover:bg-indigo-500 group-hover:text-white transition-colors",
                value = EMAIL_ADDR
            ),
            ContactItem(
                label = "WhatsApp",
                href = WHATSAPP_URL,
                iconClass = "fa-brands fa-whatsapp",
                containerClasses = "p-3 bg-emerald-500/20 rounded-lg text-emerald-400 group-hover:bg-emerald-500 group-hover:text-white transition-colors",
                value = whatsAppDisplay(WHATSAPP_URL)
            )
        )
    )
}
