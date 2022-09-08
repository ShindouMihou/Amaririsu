package pw.mihou

import kotlin.test.Test
import kotlin.test.assertEquals

class AmaririsuTest {

    @Test
    fun series() {
        val series = Amaririsu.series("https://www.scribblehub.com/series/299262/the-vampire-empress/")
        println("Trace:\n$series")

        assertEquals(299262, series.id)

        assertEquals("The Vampire Empress", series.name)
        assertEquals("Mihou", series.author.name)
    }

    @Test
    fun seriesMinified() {
        val series = Amaririsu.series("https://www.scribblehub.com/series/299262/the-vampire-empress/") {
            includeSynopsis = false
            includeGenres = false
            includeTags = false
        }

        println("Trace:\n$series")

        assertEquals(299262, series.id)

        assertEquals("The Vampire Empress", series.name)
        assertEquals("Mihou", series.author.name)
        assertEquals("", series.synopsis)
        assertEquals(true, series.genres.isEmpty())
        assertEquals(true, series.tags.isEmpty())
    }

    @Test
    fun user() {
        val user = Amaririsu.user("https://www.scribblehub.com/profile/24680/mihou/")
        println("Trace:\n$user")

        assertEquals(24680, user.id)
        assertEquals("Mihou", user.name)
    }

    @Test
    fun searchResultSeries() {
        val results = Amaririsu.search("The Vampire") {
            user.enabled = false
        }

        assertEquals(false, results.series.isEmpty())
        assertEquals(true, results.users.isEmpty())
        println("Trace:\n${results.series}")
    }

    @Test
    fun searchResultUser() {
        val results = Amaririsu.search("Miho") {
            series.enabled = false
        }

        assertEquals(false, results.users.isEmpty())
        assertEquals(true, results.series.isEmpty())
        println("Trace:\n${results.users}")
    }

}