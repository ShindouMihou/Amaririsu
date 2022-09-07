package pw.mihou

import kotlin.test.Test
import kotlin.test.assertEquals

class AmaririsuTest {

    @Test
    fun series() {
        val series = Amaririsu.series("https://www.scribblehub.com/series/299262/the-vampire-empress/")
        assertEquals("The Vampire Empress", series.name)
    }

    @Test
    fun user() {
        val user = Amaririsu.user("https://www.scribblehub.com/profile/24680/mihou/")
        assertEquals("Mihou", user.name)
    }

}