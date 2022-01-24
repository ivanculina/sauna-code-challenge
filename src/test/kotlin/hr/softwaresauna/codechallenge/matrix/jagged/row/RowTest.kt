package hr.softwaresauna.codechallenge.matrix.jagged.row

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.*

class RowTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 2])
    fun `contains returns true when in bounds`(x: Int) {
        val row = Row(
            arrayOf(
                RowPart(0, charArrayOf('A')),
                RowPart(2, charArrayOf('B'))
            )
        )

        assertTrue { row.contains(x) }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 2, 4])
    fun `contains returns false when out of bounds`(x: Int) {
        val row = Row(
            arrayOf(
                RowPart(1, charArrayOf('A')),
                RowPart(3, charArrayOf('B'))
            )
        )

        assertFalse { row.contains(x) }
    }

    @Test
    fun `contains returns false when row empty`() {
        val row = Row(emptyArray())

        assertFalse { row.contains(0) }
    }

    @ParameterizedTest
    @CsvSource(
        "0, A",
        "2, B"
    )
    fun `get returns when in bounds`(x: Int, char: Char) {
        val row = Row(
            arrayOf(
                RowPart(0, charArrayOf('A')),
                RowPart(2, charArrayOf('B'))
            )
        )

        assertEquals(char, row[x])
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 2, 4])
    fun `get throws when out of bounds`(x: Int) {
        val row = Row(
            arrayOf(
                RowPart(1, charArrayOf('A')),
                RowPart(3, charArrayOf('B'))
            )
        )

        val ex = assertThrows<IndexOutOfBoundsException> { row[x] }
        assertEquals("Invalid x $x!", ex.message)
    }

    @Test
    fun `get throws when row empty`() {
        val row = Row(emptyArray())

        val ex = assertThrows<IndexOutOfBoundsException> { row[0] }
        assertEquals("Invalid x 0!", ex.message)
    }

    @ParameterizedTest
    @CsvSource(
        "0, A",
        "2, B"
    )
    fun `getOrNull returns when in bounds`(x: Int, char: Char) {
        val row = Row(
            arrayOf(
                RowPart(0, charArrayOf('A')),
                RowPart(2, charArrayOf('B'))
            )
        )

        assertEquals(char, row.getOrNull(x))
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 2, 4])
    fun `getOrNull returns null when out of bounds`(x: Int) {
        val row = Row(
            arrayOf(
                RowPart(1, charArrayOf('A')),
                RowPart(3, charArrayOf('B'))
            )
        )

        assertNull(row.getOrNull(x))
    }

    @Test
    fun `getOrNull returns null when row empty`() {
        val row = Row(emptyArray())

        assertNull(row.getOrNull(0))
    }

    @Test
    fun `equals returns true for equal objects`() {
        val row1 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val row2 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )

        assertEquals(row1, row2)
    }

    @Test
    fun `equals returns false for non equal objects`() {
        val row1 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val row2 = Row(
            arrayOf(
                RowPart(0, "B".toCharArray())
            )
        )

        assertNotEquals(row1, row2)
    }

    @Test
    fun `hashCode returns same values for equal objects`() {
        val row1 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val row2 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )

        assertEquals(row1.hashCode(), row2.hashCode())
    }

    @Test
    fun `hashCode returns different values for non equal objects`() {
        val row1 = Row(
            arrayOf(
                RowPart(0, "A".toCharArray())
            )
        )
        val row2 = Row(
            arrayOf(
                RowPart(0, "B".toCharArray())
            )
        )

        assertNotEquals(row1.hashCode(), row2.hashCode())
    }
}
