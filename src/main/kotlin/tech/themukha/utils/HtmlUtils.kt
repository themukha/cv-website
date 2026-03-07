package tech.themukha.utils

import kotlinx.html.Tag

fun Tag.umamiEvent(eventName: String) {
    attributes["data-umami-event"] = eventName
}
