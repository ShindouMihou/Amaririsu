package pw.mihou.models.series

import pw.mihou.extensions.get
import pw.mihou.models.series.statistics.SeriesStatistics
import pw.mihou.regexes.AmaririsuRegexes

data class SeriesSearchResult(
    val name: String,
    val thumbnail: String,
    val url: String,
    val synopsis: String,
    val genres: Set<String>,
    val statistics: SeriesStatistics,
    val author: SeriesSearchResultAuthor
) {
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
