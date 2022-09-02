package pw.mihou.models.series

import pw.mihou.cache.Cacheable
import pw.mihou.extensions.get
import pw.mihou.models.user.UserMini
import pw.mihou.models.series.statistics.SeriesStatistics
import pw.mihou.models.series.statistics.SeriesUserStatistics
import pw.mihou.regexes.AmaririsuRegexes

data class Series(
    val name: String,
    val url: String,
    val cover: String,
    val synopsis: String,
    val author: UserMini,
    val statistics: SeriesStatistics,
    val genres: Set<String>,
    val tags: Set<String>,
    val userStatistics: SeriesUserStatistics
): Cacheable {
    private val matcher = AmaririsuRegexes.SERIES_LINK_REGEX.matchEntire(url)

    init {
        if (matcher == null) {
            throw IllegalArgumentException("The series link regex cannot match $url, has ScribbleHub changed their links?")
        }
    }

    val id: Int = run {
        return@run matcher!!["id"]?.toIntOrNull()
            ?: throw IllegalArgumentException("The series link regex cannot find the id from $url, has ScribbleHub " +
                    "changed their links?")
    }
}
