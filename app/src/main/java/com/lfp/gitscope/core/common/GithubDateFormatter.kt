package com.lfp.gitscope.core.common

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Formats GitHub API date strings (ISO-8601 with Z, or plain yyyy-MM-dd) for display.
 * Falls back to the original string when parsing fails (no java.time — minSdk 24 safe).
 */
object GithubDateFormatter {

    private val utc: TimeZone = TimeZone.getTimeZone("UTC")

    private val parsers: List<SimpleDateFormat> by lazy {
        listOf(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply { timeZone = utc },
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply { timeZone = utc },
            SimpleDateFormat("yyyy-MM-dd", Locale.US).apply { timeZone = utc }
        )
    }

    fun format(raw: String): String {
        val trimmed = raw.trim()
        if (trimmed.isEmpty()) return trimmed
        for (parser in parsers) {
            try {
                val date = parser.parse(trimmed) ?: continue
                return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
                    .format(date)
            } catch (_: ParseException) {
                continue
            }
        }
        return raw
    }
}
