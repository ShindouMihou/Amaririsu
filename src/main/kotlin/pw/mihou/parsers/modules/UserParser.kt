package pw.mihou.parsers.modules

import org.jsoup.nodes.Document
import pw.mihou.exceptions.DisabledUserException
import pw.mihou.exceptions.UserNotFoundException
import pw.mihou.extensions.get
import pw.mihou.extensions.getFirstElementWithClass
import pw.mihou.extensions.matchOrThrow
import pw.mihou.models.user.User
import pw.mihou.models.user.statistics.AuthorStatistics
import pw.mihou.models.user.statistics.UserStatistics
import pw.mihou.parsers.Parser
import pw.mihou.parsers.options.ParserOptions
import pw.mihou.regexes.AmaririsuRegexes
import java.io.IOException

object UserParser: Parser<User, ParserOptions> {

    override fun from(url: String, document: Document, options: ParserOptions?): User {
        val matcher = AmaririsuRegexes.USER_LINK_REGEX.matchOrThrow(url)
        val id = matcher["id"]!!.toInt()

        val errorMessage = document.getFirstElementWithClass("error_msg_profile")?.ownText()

        if (errorMessage != null) {
            when (errorMessage) {
                "This member has chosen to disable their profile." -> {
                    throw DisabledUserException(url)
                }
                "This user does not exist." -> {
                    throw UserNotFoundException(url)
                }
                else -> {
                    throw IOException("An error occurred while trying to access user ($url)'s information: $errorMessage")
                }
            }
        }

        val profileElement = document.selectFirst(".wi-fic_profile_content")!!
        val profileBox = profileElement.selectFirst(".profile > .author")!!

        val avatar = profileBox.child(0)
            .selectFirst(".fic_useravatar_profile > img")!!
            .attr("abs:src")

        val profileDetailsBox = profileBox
            .child(2)

        val name = profileDetailsBox
            .child(0)
            .ownText()

        val title = profileDetailsBox
            .child(1)
            .child(0)
            .ownText()

        val userStatisticBox = profileElement.child(1)

        var joined: String? = null
        var followers: Int? = null
        var following: Int? = null
        var comments: Int? = null

        for (element in userStatisticBox.children()) {
            when (element.ownText()) {
                "Joined:" -> {
                    joined = element.getFirstElementWithClass("pro_right")!!.ownText()
                }
                "Followers:" -> {
                    followers = element.getFirstElementWithClass("pro_right")!!.text().toInt()
                }
                "Following:" -> {
                    following = element.getFirstElementWithClass("pro_right")!!.text().toInt()
                }
                "Comments:" -> {
                    comments = element.getFirstElementWithClass("pro_right")!!.text().toInt()
                }
            }
        }

        val bio = profileElement.child(2).text()

        val overviewSection = document.selectFirst(".wi-fic_profile_content > #profile_content6")!!
        val tables = overviewSection.select("table > tbody")

        val personalInformationTable = tables[0].getElementsByTag("tr")

        var lastActive: String? = null
        var birthday: String? = null
        var gender: String? = null
        var location: String? = null
        var homepage: String? = null

        for (element in personalInformationTable) {
            val heading = element.child(0).ownText()
            val value = element.child(1).ownText()

            when (heading) {
                "Last Active:" -> {
                    lastActive = value
                }
                "Birthday:" -> {
                    birthday = value
                }
                "Gender:" -> {
                    gender = value
                }
                "Location:" -> {
                    location = value
                }
                "Homepage:" -> {
                    homepage = value
                }
            }
        }

        val authorInformationTable = tables[1].getElementsByTag("tr")

        var series: Int? = null
        var totalWords: Int? = null
        var totalPageViews: Int? = null
        var reviewsReceived: Int? = null
        var readers: Int? = null

        for (element in authorInformationTable) {
            val heading = element.child(0).ownText()
            val value = element.child(1).ownText().replace(",", "").toInt()

            when (heading) {
                "Series:" -> {
                    series = value
                }
                "Total Words:" -> {
                    totalWords = value
                }
                "Total Pageviews:" -> {
                    totalPageViews = value
                }
                "Reviews Received:" -> {
                    reviewsReceived = value
                }
                "Readers:" -> {
                    readers = value
                }
            }
        }

        return User(
            id = id,
            name = name,
            bio = bio,
            url = url,
            avatar = avatar,
            birthday = birthday!!,
            gender = gender!!,
            homepage = homepage!!,
            joined = joined!!,
            lastActive = lastActive!!,
            location = location!!,
            userStatistics = UserStatistics(
                title = title,
                following = following!!,
                comments = comments!!
            ),
            authorStatistics = AuthorStatistics(
                series = series!!,
                totalWords = totalWords!!,
                totalPageViews = totalPageViews!!,
                reviewsReceived = reviewsReceived!!,
                readers = readers!!,
                followers = followers!!
            )
        )
    }

}