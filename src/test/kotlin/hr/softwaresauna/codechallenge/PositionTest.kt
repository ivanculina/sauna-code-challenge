package hr.softwaresauna.codechallenge

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PositionTest {

    @Test
    fun `up returns correct position`() {
        assertEquals(Position(0, 0), Position(0, 1).up())
    }

    @Test
    fun `down returns correct position`() {
        assertEquals(Position(0, 1), Position(0, 0).down())
    }

    @Test
    fun `right returns correct position`() {
        assertEquals(Position(1, 0), Position(0, 0).right())
    }

    @Test
    fun `left returns correct position`() {
        assertEquals(Position(0, 0), Position(1, 0).left())
    }
}
