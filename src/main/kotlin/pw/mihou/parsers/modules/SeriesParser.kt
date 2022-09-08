package pw.mihou.parsers.modules

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import pw.mihou.exceptions.SeriesNotFoundException
import pw.mihou.extensions.get
import pw.mihou.extensions.getFirstElementWithClass
import pw.mihou.extensions.matchOrThrow
import pw.mihou.models.user.UserMini
import pw.mihou.models.series.Series
import pw.mihou.models.series.statistics.SeriesRatingStatistic
import pw.mihou.models.series.statistics.SeriesStatistics
import pw.mihou.models.series.statistics.SeriesUserStatistics
import pw.mihou.parsers.Parser
import pw.mihou.parsers.options.ParserOptions
import pw.mihou.regexes.AmaririsuRegexes

object SeriesParser: Parser<Series, ParserOptions> {

    override fun from(url: String, document: Document, options: ParserOptions?): Series {
        if (document.getFirstElementWithClass("error_msg_404") != null) {
            throw SeriesNotFoundException(url)
        }

        val matcher = AmaririsuRegexes.SERIES_LINK_REGEX.matchOrThrow(url)
        val id = matcher["id"]!!.toInt()

        val authorHref = document.selectFirst(".author > div[property=\"author\"] > span > a")!!

        var views: String? = null
        var favorites: Int? = null
        var chapters: Int? = null
        var chaptersPerWeek: Int? = null
        var readers: Int? = null

        for (element in document.select(".fic_stats > .st_item")) {
            val classification = element.getFirstElementWithClass("mb_stat")!!.text()
            val value = element.ownText()

            when (classification) {
                "Views" -> {
                    views = value
                }
                "Favorites" -> {
                    favorites = value.toInt()
                }
                "Chapters" -> {
                    chapters = value.toInt()
                }
                "Chapters/Week" -> {
                    chaptersPerWeek = value.toInt()
                }
                "Readers" -> {
                    readers = value.toInt()
                }
            }
        }

        val ratingElement = document.selectFirst("#chk_ficrate > #ratefic_user > span")!!.getElementsByTag("span")
        val userStatisticsElement = document.getFirstElementWithClass("statUser")!!

        return Series(
            id = id,
            name = document.getFirstElementWithClass("fic_title")!!
                .text(),
            cover = document.selectFirst(".novel-cover > .fic_image > img")!!
                .attr("abs:src"),
            synopsis = document.select(".wi_fic_desc")
                .joinToString("\n") { it.text() },
            url = url,
            author = UserMini(
                name = authorHref.getFirstElementWithClass("auth_name_fic")!!
                    .text(),
                url = authorHref.attr("abs:href"),
                id = AmaririsuRegexes.USER_LINK_REGEX
                    .matchOrThrow(authorHref.attr("abs:href"))["id"]!!
                    .toInt(),
                avatar = document.selectFirst("div.fic_useravatar > img")!!
                    .attr("abs:src")
            ),
            genres = document.select(".wi_fic_genre > span[property=\"genre\"] > a")
                .map { it.text() }
                .toHashSet(),
            tags = document.select(".wi_fic_showtags > .wi_fic_showtags_inner > a")
                .map { it.text() }
                .toHashSet(),
            statistics = SeriesStatistics(
                views!!,
                favorites!!,
                chapters!!,
                chaptersPerWeek!!,
                readers!!,
                SeriesRatingStatistic(
                    value = ratingElement[1]!!.ownText().toDouble(),
                    countOfUsersWhoRated = ratingElement[2]!!
                        .getFirstElementWithClass("rate_more")!!
                        .text()
                        .removeSuffix(" ratings").toInt()
                ),
                lastChapterUpdate = document
                    .selectFirst(".widget_fic_similar")!!
                    .select("li")
                    .last()!!
                    .getElementsByTag("span")[2]!!
                    .attr("title")
                    .removePrefix("Last updated: ")
            ),
            userStatistics = SeriesUserStatistics(
                usersReading = getUserStatFrom(userStatisticsElement.getElementsByClass("stat2")[0]!!),
                usersWhoPlanToRead = getUserStatFrom(userStatisticsElement.getElementsByClass("stat2")[1]!!),
                usersWhoHaveCompleted = getUserStatFrom(userStatisticsElement.getFirstElementWithClass("stat5")!!),
                usersWhoDropped = getUserStatFrom(userStatisticsElement.getFirstElementWithClass("stat3")!!),
                usersWhoPaused = getUserStatFrom(userStatisticsElement.getFirstElementWithClass("stat4")!!)
            )
        )
    }

    private fun getUserStatFrom(element: Element): Int = element.getFirstElementWithClass("sucnt")!!.text().toInt()

}