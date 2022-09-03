package pw.mihou.models.series

import pw.mihou.models.series.statistics.SeriesStatistics

data class SeriesSearchResult(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val url: String,
    val synopsis: String,
    val genres: Set<String>,
    val statistics: SeriesStatistics,
    val author: SeriesSearchResultAuthor
)
