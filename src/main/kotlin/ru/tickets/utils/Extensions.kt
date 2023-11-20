package ru.tickets.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

inline fun <reified T> T.logger(): Logger =
    LoggerFactory.getLogger(if (T::class.isCompanion) T::class.java.enclosingClass else T::class.java)

/**
 *You can use this method to get date time with zone id
 * LocalDateTime.now(ZoneId.of("Europe/Moscow").asDate() - return Date() with ZoneId
 */
fun LocalDateTime?.asDate() = this?.atZone(ZoneId.systemDefault())?.toInstant()?.let { Date.from(it) }

fun <T> printFirstFive(list: Collection<T>): String {
    val maxSize = 5
    return if (list.size > maxSize) list.take(maxSize)
        .toString() + "... and ${list.size - maxSize} items" else list.toString()
}

fun yyyyMMddDateFormat(): DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun yyyyMMddHHmmssDateFormat(): DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")

fun yyyyMMddHHmmDateFormat(): DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

fun String.onlyLettersAndNumber() = Regex("[^А-Яа-яA-Za-z0-9]").replace(this, "").lowercase()

fun List<String>.sqlSeparator() = this.joinToString(separator = ",") { "'$it'" }

fun String.color(): String {
    val hash = this.hashCode()
    val r = (hash and 0xFF0000 shr 16)
    val g = (hash and 0x00FF00 shr 8)
    val b = (hash and 0x0000FF)
    return "rgb($r, $g, $b)"
}

fun String.logo(): String {
    val words = this.trim().split(" ")
    return if (words.size > 1) {
        words[0].first().uppercase() + words[1].first().uppercase()
    } else {
        words[0].first().uppercase()
    }
}

fun String.capitalizeWords(): String {
    return this
        .trim()
        .split("\\s+".toRegex())
        .joinToString(" ") { it.lowercase().replaceFirstChar(Char::uppercaseChar) }
}