package pw.mihou.models.user

import pw.mihou.extensions.get
import pw.mihou.regexes.AmaririsuRegexes

data class UserMini(val name: String, val avatar: String, val url: String) {

    private val matcher = AmaririsuRegexes.USER_LINK_REGEX.matchEntire(url)

    init {
        if (matcher == null) {
            throw IllegalArgumentException("The user link regex cannot match $url, has ScribbleHub changed their links?")
        }
    }

    val id: Int = run {
        return@run matcher!!["id"]?.toIntOrNull()
            ?: throw IllegalArgumentException("The user link regex cannot find the id from $url, has ScribbleHub " +
                    "changed their links?")
    }
}
