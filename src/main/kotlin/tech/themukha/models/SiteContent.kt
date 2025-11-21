package tech.themukha.models

import kotlinx.serialization.Serializable

@Serializable
data class SiteContent(
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
    val experiences: List<Experience>
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
