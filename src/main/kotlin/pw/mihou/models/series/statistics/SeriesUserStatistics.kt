package pw.mihou.models.series.statistics

data class SeriesUserStatistics(
    val usersReading: Int,
    val usersWhoPlanToRead: Int,
    val usersWhoHaveCompleted: Int,
    val usersWhoPaused: Int,
    val usersWhoDropped: Int
)
