package pw.mihou.models.user

import pw.mihou.Amaririsu

data class UserResultOrAuthor(val id: Int, val name: String, val avatar: String, val url: String) {
    fun expand(): User = Amaririsu.user(url)
}