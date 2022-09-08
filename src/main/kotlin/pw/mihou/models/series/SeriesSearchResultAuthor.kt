package pw.mihou.models.series

import pw.mihou.Amaririsu
import pw.mihou.models.user.User

data class SeriesSearchResultAuthor(val id: Int, val name: String, val url: String) {
    fun expand(): User = Amaririsu.user(url)
}