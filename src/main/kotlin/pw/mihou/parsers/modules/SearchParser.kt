package pw.mihou.parsers.modules

import org.jsoup.nodes.Document
import pw.mihou.models.SearchResult
import pw.mihou.models.series.SeriesSearchResult
import pw.mihou.models.user.UserResultOrAuthor
import pw.mihou.parsers.Parser
import pw.mihou.parsers.modules.search.SeriesNodeParser
import pw.mihou.parsers.modules.search.UserNodeParser
import pw.mihou.parsers.options.modules.SearchOptions

object SearchParser: Parser<SearchResult, SearchOptions> {

    override fun from(url: String, document: Document, options: SearchOptions): SearchResult {
        val users: MutableSet<UserResultOrAuthor> = mutableSetOf()
        val series: MutableSet<SeriesSearchResult> = mutableSetOf()

        if (options.includeSeries) {
            document.select(".search_main_box").forEach { element ->
                series.add(SeriesNodeParser.from(document, element))
            }
        }

        if (options.includeUsers) {
            document.select(".sb_box > .s_user_link").forEach { element ->
                users.add(UserNodeParser.from(document, element))
            }
        }

        return SearchResult(users = users, series = series)
    }

}