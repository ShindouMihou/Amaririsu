package pw.mihou.models.user

import pw.mihou.cache.Cacheable
import pw.mihou.models.user.statistics.AuthorStatistics
import pw.mihou.models.user.statistics.UserStatistics

data class User(
    val id: Int,
    val name: String,
    val avatar: String,
    val bio: String,
    val birthday: String,
    val gender: String,
    val location: String,
    val homepage: String,
    val lastActive: String,
    val joined: String,
    val userStatistics: UserStatistics,
    val authorStatistics: AuthorStatistics,
    val url: String
): Cacheable
