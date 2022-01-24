package hr.softwaresauna.codechallenge

import hr.softwaresauna.codechallenge.exception.*
import hr.softwaresauna.codechallenge.matrix.jagged.JaggedMatrix
import hr.softwaresauna.codechallenge.matrix.jagged.row.Row
import hr.softwaresauna.codechallenge.matrix.jagged.row.RowPart
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class InputLoaderTest {

    @Test
    fun `load returns matrix and start position when valid map`() {
        val row1 = Row(
            arrayOf(
                RowPart(2, "@---A---+".toCharArray())
            )
        )
        val row2 = Row(
            arrayOf(
                RowPart(10, "|".toCharArray())
            )
        )
        val row3 = Row(
            arrayOf(
                RowPart(2, "x-B-+".toCharArray()),
                RowPart(10, "C".toCharArray())
            )
        )
        val row4 = Row(
            arrayOf(
                RowPart(6, "|".toCharArray()),
                RowPart(10, "|".toCharArray())
            )
        )
        val row5 = Row(
            arrayOf(
                RowPart(6, "+---+".toCharArray())
            )
        )
        val expectedMatrix = JaggedMatrix(arrayOf(row1, row2, row3, row4, row5))

        val (matrix, start) = InputLoader().load("/map1.txt")

        assertEquals(expectedMatrix, matrix)
        assertEquals(Position(2, 0), start)
    }

    @Test
    fun `load throws when no start - map 6`() {
        val ex = assertThrows<NoStartException> { InputLoader().load("/map6.txt") }
        assertEquals("No start!", ex.message)
    }

    @Test
    fun `load throws when no end - map 7`() {
        val ex = assertThrows<NoEndException> { InputLoader().load("/map7.txt") }
        assertEquals("No end!", ex.message)
    }

    @Test
    fun `load throws when multiple starts - map 8`() {
        val ex = assertThrows<MultipleStartsException> { InputLoader().load("/map8.txt") }
        assertEquals("Multiple starts!", ex.message)
    }

    @Test
    fun `load throws when multiple ends - map 9`() {
        val ex = assertThrows<MultipleEndsException> { InputLoader().load("/map9.txt") }
        assertEquals("Multiple ends!", ex.message)
    }

    @Test
    fun `load throws when invalid chars`() {
        val ex = assertThrows<InvalidCharException> { InputLoader().load("/map-with-invalid-char.txt") }
        assertEquals("Invalid char $ in the map!", ex.message)
    }

    @Test
    fun `load throws when invalid path`() {
        val path = "/wrong-path"

        val ex = assertThrows<IllegalArgumentException> { InputLoader().load(path) }
        assertEquals("Path $path not found!", ex.message)
    }
}
