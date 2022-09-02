package pw.mihou.regexes

object AmaririsuRegexes {

    val USER_LINK_REGEX = Regex("https://(?>www\\.)?scribblehub.com/profile/(?<id>[0-9]*)/(?<name>.*)/")
    val SERIES_LINK_REGEX = Regex("https://(?>www\\.)?scribblehub.com/series/(?<id>[0-9]*)/(?<name>.*)/")

}