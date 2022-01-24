package hr.softwaresauna.codechallenge.traveler

import hr.softwaresauna.codechallenge.InputLoader
import hr.softwaresauna.codechallenge.exception.BrokenPathException
import hr.softwaresauna.codechallenge.exception.FakeTurnException
import hr.softwaresauna.codechallenge.exception.ForksException
import hr.softwaresauna.codechallenge.exception.MultipleStartingPathsException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class TravelerTest {

    @ParameterizedTest
    @CsvSource(
        "map1.txt, ACB, @---A---+|C|+---+|+-B-x",
        "map2.txt, ABCD, @|A+---B--+|+--C-+|-||+---D--+|x",
        "map3.txt, ACB, @---A---+|||C---+|+-B-x",
        "map4.txt, GOONIES, @-G-O-+|+-+|O||+-O-N-+|I|+-+|+-I-+|ES|x",
        "map5.txt, BLAH, @B+++B|+-L-+A+++A-+Hx"
    )
    fun `travel returns expected letters and path for given map`(map: String, letters: String, path: String) {
        val (matrix, start) = InputLoader().load("/$map")

        val result = Traveler().travel(matrix, start)

        assertEquals(letters, result.letters.joinToString(""))
        assertEquals(path, result.path.joinToString(""))
    }

    @Test
    fun `travel throws when forks - map 10`() {
        val (matrix, start) = InputLoader().load("/map10.txt")

        val ex = assertThrows<ForksException> { Traveler().travel(matrix, start) }
        assertEquals("Forks at Position(x=10, y=2)!", ex.message)
    }

    @Test
    fun `travel throws when broken path - map 11`() {
        val (matrix, start) = InputLoader().load("/map11.txt")

        val ex = assertThrows<BrokenPathException> { Traveler().travel(matrix, start) }
        assertEquals("Broken path at Position(x=8, y=1)!", ex.message)
    }

    @Test
    fun `travel throws when multiple starting paths - map 12`() {
        val (matrix, start) = InputLoader().load("/map12.txt")

        val ex = assertThrows<MultipleStartingPathsException> { Traveler().travel(matrix, start) }
        assertEquals("Multiple starting paths!", ex.message)
    }

    @Test
    fun `travel throws when fake turn - map 13`() {
        val (matrix, start) = InputLoader().load("/map13.txt")

        val ex = assertThrows<FakeTurnException> { Traveler().travel(matrix, start) }
        assertEquals("Fake turn at Position(x=6, y=0)!", ex.message)
    }

}
