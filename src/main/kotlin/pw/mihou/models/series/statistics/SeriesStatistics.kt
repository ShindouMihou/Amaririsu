package pw.mihou.models.series.statistics

data class SeriesStatistics(
    val views: String,
    val favorites: Int,
    val chapters: Int,
    val chaptersPerWeek: Int,
    val readers: Int,
    val rating: SeriesRatingStatistic,
    val reviews: Int? = null,
    val wordCount: String? = null,
    val lastChapterUpdate: String
)
