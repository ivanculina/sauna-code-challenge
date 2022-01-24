package hr.softwaresauna.codechallenge.matrix.jagged

import hr.softwaresauna.codechallenge.Position
import hr.softwaresauna.codechallenge.matrix.jagged.row.Row
import hr.softwaresauna.codechallenge.matrix.jagged.row.RowPart
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.*

class JaggedMatrixTest {

    @Test
    fun `contains returns true when in bounds`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val rows = arrayOf(row)
        val jaggedMatrix = JaggedMatrix(rows)

        assertTrue { jaggedMatrix.contains(Position(0, 0)) }
    }

    @ParameterizedTest
    @CsvSource(
        "0, 1",
        "1, 0"
    )
    fun `contains returns false when x or y out of bounds`(x: Int, y: Int) {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val rows = arrayOf(row)
        val jaggedMatrix = JaggedMatrix(rows)

        assertFalse { jaggedMatrix.contains(Position(x, y)) }
    }

    @Test
    fun `get returns character when in bounds`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val rows = arrayOf(row)
        val jaggedMatrix = JaggedMatrix(rows)

        assertEquals('A', jaggedMatrix[Position(0, 0)])
    }

    @Test
    fun `get throws when y out of bounds`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val rows = arrayOf(row)
        val jaggedMatrix = JaggedMatrix(rows)

        val ex = assertThrows<IndexOutOfBoundsException> { jaggedMatrix[Position(0, 1)] }
        assertEquals("Invalid y 1, size is ${rows.size}", ex.message)
    }

    @Test
    fun `get throws when x out of bounds`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val rows = arrayOf(row)
        val jaggedMatrix = JaggedMatrix(rows)

        assertThrows<IndexOutOfBoundsException> { jaggedMatrix[Position(1, 0)] }
    }

    @Test
    fun `getOrNull returns character when in bounds`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val rows = arrayOf(row)
        val jaggedMatrix = JaggedMatrix(rows)

        assertEquals('A', jaggedMatrix.getOrNull(Position(0, 0)))
    }

    @ParameterizedTest
    @CsvSource(
        "0, 1",
        "1, 0"
    )
    fun `getOrNull returns null when x or y out of bounds`(x: Int, y: Int) {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val rows = arrayOf(row)
        val jaggedMatrix = JaggedMatrix(rows)

        assertNull(jaggedMatrix.getOrNull(Position(x, y)))
    }

    @Test
    fun `equals returns true for equal objects`() {
        val row1 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val matrix1 = JaggedMatrix(arrayOf(row1))

        val row2 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val matrix2 = JaggedMatrix(arrayOf(row2))

        assertEquals(matrix1, matrix2)
    }

    @Test
    fun `equals returns false for non equal objects`() {
        val row1 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val matrix1 = JaggedMatrix(arrayOf(row1))

        val row2 = Row(
            arrayOf(
                RowPart(0, "B".toCharArray())
            )
        )
        val matrix2 = JaggedMatrix(arrayOf(row2))

        assertNotEquals(matrix1, matrix2)
    }

    @Test
    fun `hashCode returns same values for equal objects`() {
        val row1 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val matrix1 = JaggedMatrix(arrayOf(row1))

        val row2 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val matrix2 = JaggedMatrix(arrayOf(row2))

        assertEquals(matrix1.hashCode(), matrix2.hashCode())
    }

    @Test
    fun `hashCode returns different values for non equal objects`() {
        val row1 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val matrix1 = JaggedMatrix(arrayOf(row1))

        val row2 = Row(
            arrayOf(
                RowPart(0, "B".toCharArray())
            )
        )
        val matrix2 = JaggedMatrix(arrayOf(row2))

        assertNotEquals(matrix1.hashCode(), matrix2.hashCode())
    }
}
