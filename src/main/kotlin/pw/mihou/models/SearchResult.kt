package pw.mihou.models

import pw.mihou.cache.Cacheable
import pw.mihou.models.series.SeriesSearchResult
import pw.mihou.models.user.UserMini

data class SearchResult(val series: Set<SeriesSearchResult>, val users: Set<UserMini>): Cacheable
