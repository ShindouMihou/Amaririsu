package pw.mihou.parsers.modules.search

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import pw.mihou.extensions.get
import pw.mihou.extensions.getFirstElementWithClass
import pw.mihou.extensions.matchOrThrow
import pw.mihou.models.user.UserResultOrAuthor
import pw.mihou.parsers.NodeParser
import pw.mihou.parsers.options.modules.SearchOptions
import pw.mihou.regexes.AmaririsuRegexes

object UserNodeParser: NodeParser<UserResultOrAuthor, SearchOptions> {

    override fun from(document: Document, element: Element, options: SearchOptions): UserResultOrAuthor {
        val link = element.attr("abs:href")
        val matcher = AmaririsuRegexes.USER_LINK_REGEX.matchOrThrow(link)
        val id = matcher["id"]!!.toInt()

        val imageElement = element.getFirstElementWithClass("s_user_results")!!
            .getFirstElementWithClass("sur_image")!!
            .getElementsByTag("img")
            .first()!!

        var avatar = imageElement.attr("abs:src")

        if (options.user.largeAvatars) {
            avatar = avatar.replaceFirst("/s/", "/l/")
        }

        return UserResultOrAuthor(
            id = id,
            name = imageElement.attr("alt"),
            avatar = avatar,
            link
        )
    }

}