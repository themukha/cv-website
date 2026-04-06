package tech.themukha.utils

import tech.themukha.models.Experience
import tech.themukha.models.SiteContent

/**
 * Central place for all SEO / GEO (Generative Engine Optimization) content generation.
 * Every builder derives URLs from [SiteContent.domain] so nothing is hard-coded.
 */
object SeoUtils {

    /* ───── shared SEO constants ───── */

    /** Alternate name spellings for structured data & meta tags. */
    val ALTERNATE_NAMES = listOf("Georgii Mukha", "Георгий Муха", "Георгий Муха QA")

    /** Job titles used in structured data (covers all target search keywords). */
    val JOB_TITLES = listOf(
        "Senior QA Backend Engineer",
        "SDET",
        "QA Auto Engineer",
        "AI QA Engineer",
        "Software Development Engineer in Test",
    )

    /** Skills / technologies for structured data `knowsAbout`. */
    val KNOWS_ABOUT = listOf(
        "Quality Assurance", "Test Automation", "Backend Testing",
        "Kotlin", "Java", "gRPC", "Kafka", "REST API", "WebSocket",
        "PostgreSQL", "ClickHouse", "Docker", "Jenkins", "CI/CD",
        "Load Testing", "AI QA", "SDET", "FIX Protocol",
        "Grafana", "Performance Testing",
    )

    /** Build the full site URL from the domain stored in content. */
    fun siteUrl(content: SiteContent): String = "https://${content.domain}"

    /* ───── meta tag builders ───── */

    fun seoTitle(content: SiteContent): String =
        "${content.fullName} — Senior QA Engineer, SDET & AI QA Engineer | ${content.domain}"

    fun seoDescription(content: SiteContent): String {
        val names = ALTERNATE_NAMES.joinToString(" / ")
        return "${content.fullName} ($names) — Senior QA Backend Engineer & SDET " +
                "with 5+ years of experience in test automation, Kotlin, Java, and AI-driven QA."
    }

    fun seoKeywords(content: SiteContent): String {
        val nameVariants = listOf(content.fullName) + ALTERNATE_NAMES
        val roles = listOf(
            "QA Engineer", "AI QA Engineer", "Software Development Engineer in Test",
            "SDET", "QA Auto Engineer", "Senior QA Engineer", "QA Backend Engineer",
        )
        val extras = listOf("test automation", "Kotlin", "Java", "AQA", "QA Tech Lead")
        return (nameVariants + roles + extras).joinToString(", ")
    }

    fun ogImageUrl(content: SiteContent): String = "${siteUrl(content)}${content.photoUrl}"

    /* ───── JSON-LD structured data ───── */

    fun buildJsonLd(content: SiteContent): String {
        val url = siteUrl(content)
        val imageUrl = ogImageUrl(content)
        val description = seoDescription(content)
        val sameAs = listOf(content.linkedin, content.telegram, content.github)

        return """
        {
            "@context": "https://schema.org",
            "@graph": [
                ${personJsonLd(content, url, imageUrl, description, sameAs)},
                ${websiteJsonLd(content, url, description)},
                ${faqJsonLd(content)}
            ]
        }
        """.trimIndent()
    }

    private fun personJsonLd(
        content: SiteContent,
        url: String,
        imageUrl: String,
        description: String,
        sameAs: List<String>,
    ): String {
        val altNames = ALTERNATE_NAMES.joinToString(", ") { "\"$it\"" }
        val titles = JOB_TITLES.joinToString(", ") { "\"$it\"" }
        val skills = KNOWS_ABOUT.joinToString(", ") { "\"$it\"" }
        val sameAsJson = sameAs.joinToString(", ") { "\"$it\"" }

        return """
        {
            "@type": "Person",
            "@id": "$url/#person",
            "name": "${content.fullName}",
            "alternateName": [$altNames],
            "givenName": "${content.fullName.substringBefore(' ')}",
            "familyName": "${content.fullName.substringAfter(' ')}",
            "jobTitle": [$titles],
            "description": "$description",
            "url": "$url",
            "image": "$imageUrl",
            "email": "mailto:${content.email}",
            "sameAs": [$sameAsJson],
            "knowsAbout": [$skills],
            "knowsLanguage": ["en", "ru"],
            "nationality": "Russian",
            "workLocation": {
                "@type": "Place",
                "name": "Serbia"
            }
        }
        """.trimIndent()
    }

    private fun websiteJsonLd(content: SiteContent, url: String, description: String): String = """
        {
            "@type": "WebSite",
            "@id": "$url/#website",
            "url": "$url",
            "name": "${content.fullName} — Senior QA Engineer & SDET Portfolio",
            "description": "$description",
            "publisher": { "@id": "$url/#person" },
            "inLanguage": "en"
        }
    """.trimIndent()

    private fun faqJsonLd(content: SiteContent): String {
        data class Faq(val question: String, val answer: String)

        val stackList = content.commonStack.joinToString(", ") { it.title }
        val faqs = listOf(
            Faq(
                "Who is ${content.fullName}?",
                "${content.fullName} (also known as ${ALTERNATE_NAMES.joinToString(" / ")}) " +
                        "is a Senior QA Backend Engineer and SDET with over 5 years of experience " +
                        "in test automation, backend testing, and AI-driven quality assurance. " +
                        "He specializes in Kotlin, Java, gRPC, Kafka, and building AI-powered QA tools."
            ),
            Faq(
                "What technologies does ${content.fullName} work with?",
                "${content.fullName} works with $stackList, and AI/LLM technologies. " +
                        "He has experience building QA AI services using models like gemma-3-27b."
            ),
            Faq(
                "Where is ${content.fullName} located and is he available for hire?",
                "${content.fullName} is currently based in Serbia and is available for remote work globally. " +
                        "He is open to new offers and can be contacted via Telegram, LinkedIn, or email at ${content.email}."
            ),
            Faq(
                "What are ${content.fullName}'s key achievements?",
                "Key achievements include: developing a QA AI service that reduced testing time significantly, " +
                        "implementing API test coverage from 0% to 98% resulting in 12x reduction in regulatory fines at T-Bank, " +
                        "implementing load testing that detected 500x performance degradations, " +
                        "and building complete QA processes from scratch at multiple companies."
            ),
        )

        val entries = faqs.joinToString(",\n") { faq ->
            """
            {
                "@type": "Question",
                "name": "${faq.question}",
                "acceptedAnswer": {
                    "@type": "Answer",
                    "text": "${faq.answer}"
                }
            }
            """.trimIndent()
        }

        return """
        {
            "@type": "FAQPage",
            "mainEntity": [$entries]
        }
        """.trimIndent()
    }

    /* ───── robots.txt & sitemap.xml ───── */

    fun buildRobotsTxt(content: SiteContent): String {
        val url = siteUrl(content)
        return """
            User-agent: *
            Allow: /

            Sitemap: $url/sitemap.xml

            # AI / LLM agents
            User-agent: GPTBot
            Allow: /

            User-agent: Google-Extended
            Allow: /

            User-agent: ChatGPT-User
            Allow: /

            User-agent: anthropic-ai
            Allow: /

            User-agent: ClaudeBot
            Allow: /

            User-agent: PerplexityBot
            Allow: /

            User-agent: Applebot-Extended
            Allow: /

            # LLM-readable content
            # See https://llmstxt.org/
            # llms.txt: $url/llms.txt
            # llms-full.txt: $url/llms-full.txt
        """.trimIndent()
    }

    fun buildSitemapXml(content: SiteContent): String {
        val url = siteUrl(content)
        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                <url>
                    <loc>$url/</loc>
                    <changefreq>monthly</changefreq>
                    <priority>1.0</priority>
                </url>
            </urlset>
        """.trimIndent()
    }

    /* ───── llms.txt / llms-full.txt ───── */

    fun buildLlmsTxt(content: SiteContent): String =
        fillTemplate("seo/llms.txt", buildPlaceholders(content))

    fun buildLlmsFullTxt(content: SiteContent): String =
        fillTemplate("seo/llms-full.txt", buildPlaceholders(content))

    private fun buildPlaceholders(content: SiteContent): Map<String, String> {
        val url = siteUrl(content)
        return mapOf(
            "fullName" to content.fullName,
            "tagline" to content.tagline,
            "seoDescription" to seoDescription(content),
            "about" to content.about.replace("<[^>]*>".toRegex(), ""),
            "alternateNames" to ALTERNATE_NAMES.joinToString(", "),
            "jobTitles" to JOB_TITLES.joinToString(" / "),
            "stack" to content.commonStack.joinToString(", ") { it.title },
            "links" to buildLinksSection(content, url),
            "experiences" to content.experiences.joinToString("\n\n") { formatExperience(it) },
            "projectActivities" to formatProjectActivities(content.projectActivities),
            "contacts" to buildContactsSection(content),
            "siteUrl" to url,
        )
    }

    private fun fillTemplate(resourcePath: String, placeholders: Map<String, String>): String {
        val template = object {}.javaClass.classLoader
            .getResource(resourcePath)
            ?.readText()
            ?: error("SEO template not found: $resourcePath")

        return placeholders.entries.fold(template) { text, (key, value) ->
            text.replace("{{$key}}", value)
        }
    }

    private fun buildLinksSection(content: SiteContent, url: String): String = buildString {
        appendLine("- Website: $url")
        appendLine("- LinkedIn: ${content.linkedin}")
        appendLine("- Telegram: ${content.telegram}")
        appendLine("- Email: ${content.email}")
        appendLine("- CV (English): $url${content.cvEnUrl}")
        append("- CV (Russian): $url${content.cvRuUrl}")
    }

    private fun buildContactsSection(content: SiteContent): String =
        content.contacts.joinToString("\n") { "- ${it.label}: ${it.value}" }

    private fun formatExperience(exp: Experience): String = buildString {
        appendLine("### ${exp.company} — ${exp.role}")
        appendLine("**Period**: ${exp.period}")
        appendLine()
        if (exp.aboutPosition.isNotBlank()) appendLine(exp.aboutPosition)
        if (!exp.responsibilities.isNullOrEmpty()) {
            appendLine()
            appendLine("**Responsibilities**:")
            exp.responsibilities.forEach { appendLine("- $it") }
        }
        if (exp.results.isNotEmpty()) {
            appendLine()
            appendLine("**Key Results**:")
            exp.results.forEach { appendLine("- $it") }
        }
        if (exp.stack.isNotEmpty()) {
            appendLine()
            append("**Tech Stack**: ${exp.stack.joinToString(", ")}")
        }
    }

    private fun formatProjectActivities(activities: List<Experience>): String {
        if (activities.isEmpty()) return ""
        return buildString {
            appendLine("## Project Activities")
            appendLine()
            append(activities.joinToString("\n\n") { formatExperience(it) })
        }
    }
}
