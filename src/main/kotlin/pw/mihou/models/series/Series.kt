package pw.mihou.models.series

import pw.mihou.cache.Cacheable
import pw.mihou.models.user.UserMini
import pw.mihou.models.series.statistics.SeriesStatistics
import pw.mihou.models.series.statistics.SeriesUserStatistics

data class Series(
    val id: Int,
    val name: String,
    val url: String,
    val cover: String,
    val synopsis: String,
    val author: UserMini,
    val statistics: SeriesStatistics,
    val genres: Set<String>,
    val tags: Set<String>,
    val userStatistics: SeriesUserStatistics
): Cacheable
