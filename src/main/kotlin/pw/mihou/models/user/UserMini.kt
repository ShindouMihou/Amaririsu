package pw.mihou.models.user

import pw.mihou.Amaririsu

data class UserMini(val id: Int, val name: String, val avatar: String, val url: String) {
    fun expand(): User = Amaririsu.user(url)
}