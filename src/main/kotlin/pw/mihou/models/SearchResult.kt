package pw.mihou.models

import pw.mihou.cache.Cacheable
import pw.mihou.models.series.SeriesSearchResult
import pw.mihou.models.user.UserResultOrAuthor

data class SearchResult(val series: Set<SeriesSearchResult>, val users: Set<UserResultOrAuthor>): Cacheable
