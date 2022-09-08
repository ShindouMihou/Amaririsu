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
    fun user() {
        val user = Amaririsu.user("https://www.scribblehub.com/profile/24680/mihou/")
        println("Trace:\n$user")

        assertEquals(24680, user.id)
        assertEquals("Mihou", user.name)
    }

    @Test
    fun searchResultSeries() {
        val results = Amaririsu.search("The Vampire")

        assertEquals(false, results.series.isEmpty())
        println("Trace:\n${results.series}")
    }

    @Test
    fun searchResultUser() {
        val results = Amaririsu.search("Miho")

        assertEquals(false, results.users.isEmpty())
        println("Trace:\n${results.users}")
    }

}