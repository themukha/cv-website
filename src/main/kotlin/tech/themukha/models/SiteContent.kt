package tech.themukha.models

import kotlinx.serialization.Serializable

@Serializable
data class SiteContent(
    val navigation: List<NavItem>,
    val fullName: String,
    val tagline: String,
    val about: String,
    val telegram: String,
    val whatsapp: String,
    val email: String,
    val linkedin: String,
    val photoUrl: String,
    val cvEnUrl: String,
    val cvRuUrl: String,
    val commonStack: List<StackItem>,
    val experiences: List<Experience>,
    val contacts: List<ContactItem>
)

@Serializable
data class Experience(
    val company: String,
    val role: String,
    val period: String,
    val aboutPosition: String,
    val responsibilities: List<String>,
    val results: List<String>,
    val stack: List<String>
)

@Serializable
data class NavItem(
    val text: String,
    val href: String
)

@Serializable
data class StackItem(
    val iconUrl: String,
    val title: String,
    val subtitle: String
)

@Serializable
data class ContactItem(
    val label: String,
    val href: String,
    val iconClass: String,
    val containerClasses: String,
    val value: String
)
