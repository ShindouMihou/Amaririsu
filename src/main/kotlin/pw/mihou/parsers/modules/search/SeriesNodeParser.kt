package pw.mihou.parsers.modules.search

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import pw.mihou.extensions.get
import pw.mihou.extensions.getFirstElementWithClass
import pw.mihou.extensions.matchOrThrow
import pw.mihou.models.series.SeriesSearchResult
import pw.mihou.models.series.SeriesSearchResultAuthor
import pw.mihou.models.series.statistics.SeriesRatingStatistic
import pw.mihou.models.series.statistics.SeriesStatistics
import pw.mihou.parsers.NodeParser
import pw.mihou.parsers.options.modules.SearchOptions
import pw.mihou.regexes.AmaririsuRegexes

object SeriesNodeParser: NodeParser<SeriesSearchResult, SearchOptions> {

    override fun from(document: Document, element: Element, options: SearchOptions): SeriesSearchResult {
        val cover = element.getFirstElementWithClass("search_img")!!
            .child(0)
            .getElementsByTag("img")
            .first()!!
            .attr("abs:src")

        val searchBodyElement = element.selectFirst(".search_body")!!
        val titleElement = searchBodyElement.selectFirst(".search_title > a")!!

        val title = titleElement.text()
        val link = titleElement.attr("abs:href")

        val matcher = AmaririsuRegexes.SERIES_LINK_REGEX.matchOrThrow(link)
        val id = matcher["id"]!!.toInt()

        var synopsis = searchBodyElement.ownText()

        val hiddenSynopsisElement = searchBodyElement.getFirstElementWithClass("testhide")

        if (hiddenSynopsisElement != null) {
            synopsis += "\n${hiddenSynopsisElement.ownText()}"
        }

        val genres = mutableSetOf<String>()
        val genresElements = searchBodyElement.select(".search_genre > .fic_genre")

        for (genre in genresElements) {
            genres.add(genre.ownText())
        }

        val statisticElements = searchBodyElement.select(".search_stats > .nl_stat")

        var views: String? = null
        var favorites: Int? = null
        var chapters: Int? = null
        var chaptersPerWeek: Int? = null
        var readers: Int? = null
        var reviews: Int? = null
        var words: String? = null
        var lastUpdated: String? = null
        var author: SeriesSearchResultAuthor? = null

        for (statisticElement in statisticElements) {
            when (statisticElement.attr("title")) {
                "" -> {
                    val texts = statisticElement.ownText().split(" ", limit = 2)

                    when (texts[1]) {
                        "Views" -> {
                            views = texts[0]
                        }
                        "Favorites" -> {
                            favorites = texts[0].toInt()
                        }
                        "Chapters" -> {
                            chapters = texts[0].toInt()
                        }
                        "Chapters/Week" -> {
                            chaptersPerWeek = texts[0].toInt()
                        }
                        "Readers" -> {
                            readers = texts[0].toInt()
                        }
                        "Reviews" -> {
                            reviews = texts[0].toInt()
                        }
                        "Words" -> {
                            words = texts[0]
                        }
                    }
                }
                "Last Updated" -> {
                    lastUpdated = statisticElement.ownText()
                }
                "Author" -> {
                    val authorElement = statisticElement.selectFirst("span > a")!!

                    val authorUrl = authorElement.attr("abs:href")

                    val authorMatcher = AmaririsuRegexes.USER_LINK_REGEX.matchOrThrow(authorUrl)
                    val authorId = authorMatcher["id"]!!.toInt()

                    author = SeriesSearchResultAuthor(
                        id = authorId,
                        name = authorElement.ownText(),
                        url = authorUrl
                    )
                }
            }
        }

        val rating = element.selectFirst(".search_img > .search_ratings")!!.ownText()
            .removeSurrounding("(",  ")")
            .toDouble()

        return SeriesSearchResult(
            id = id,
            name = title,
            thumbnail = cover,
            url = link,
            synopsis = synopsis,
            genres = genres,
            statistics = SeriesStatistics(
                views = views!!,
                favorites = favorites!!,
                chapters = chapters!!,
                chaptersPerWeek = chaptersPerWeek!!,
                readers = readers!!,
                reviews = reviews!!,
                wordCount = words!!,
                lastChapterUpdate = lastUpdated!!,
                rating = SeriesRatingStatistic(
                    value = rating,
                    countOfUsersWhoRated = null
                )
            ),
            author = author!!
        )
    }

}