package pw.mihou.parsers.options.modules

import pw.mihou.parsers.options.ParserOptions
import pw.mihou.parsers.options.modules.search.SearchSeriesOptions
import pw.mihou.parsers.options.modules.search.SearchUserOptions

class SearchOptions: ParserOptions() {
    var series: SearchSeriesOptions = SearchSeriesOptions()
    val user: SearchUserOptions = SearchUserOptions()
}