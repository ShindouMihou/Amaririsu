package pw.mihou.models.user.statistics

data class AuthorStatistics(
    val series: Int,
    val totalWords: Int,
    val totalPageViews: Int,
    val reviewsReceived: Int,
    val readers: Int,
    val followers: Int
)
