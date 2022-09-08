package pw.mihou.parsers.modules

import org.jsoup.nodes.Document
import pw.mihou.models.SearchResult
import pw.mihou.models.series.SeriesSearchResult
import pw.mihou.models.user.UserMini
import pw.mihou.parsers.Parser
import pw.mihou.parsers.modules.search.SeriesNodeParser
import pw.mihou.parsers.modules.search.UserNodeParser

object SearchParser: Parser<SearchResult> {

    override fun from(url: String, document: Document): SearchResult {
        val users: MutableSet<UserMini> = mutableSetOf()
        val series: MutableSet<SeriesSearchResult> = mutableSetOf()

        document.select(".search_main_box").forEach { element ->
            series.add(SeriesNodeParser.from(document, element))
        }

        document.select(".sb_box > .s_user_link").forEach { element ->
            users.add(UserNodeParser.from(document, element))
        }

        return SearchResult(users = users, series = series)
    }

}