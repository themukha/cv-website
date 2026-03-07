package tech.themukha.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object AppLogger {
    val logger: Logger = LoggerFactory.getLogger("cv-website")

    fun info(message: String) = logger.info(message)
    fun warn(message: String) = logger.warn(message)
    fun error(message: String, throwable: Throwable? = null) = throwable
        ?.let { logger.error(message, it) }
        ?: logger.error(message)
    fun debug(message: String) = logger.debug(message)
}
