package hr.softwaresauna.codechallenge.matrix.jagged.row

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.*

class RowPartTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `contains returns true when in bounds`(x: Int) {
        val rowPart = RowPart(0, charArrayOf('A', 'B'))

        assertTrue(rowPart.containsIndex(x))
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 3])
    fun `contains returns false when out of bounds`(x: Int) {
        val rowPart = RowPart(1, charArrayOf('A', 'B'))

        assertFalse(rowPart.containsIndex(x))
    }

    @Test
    fun `equals returns true for equal objects`() {
        val rowPart1 = RowPart(0, "A".toCharArray())
        val rowPart2 = RowPart(0, "A".toCharArray())

        assertEquals(rowPart1, rowPart2)
    }

    @Test
    fun `equals returns false for non equal objects`() {
        val rowPart1 = RowPart(0, "A".toCharArray())
        val rowPart2 = RowPart(0, "B".toCharArray())

        assertNotEquals(rowPart1, rowPart2)
    }

    @Test
    fun `hashCode returns same values for equal objects`() {
        val rowPart1 = RowPart(0, "A".toCharArray())
        val rowPart2 = RowPart(0, "A".toCharArray())

        assertEquals(rowPart1.hashCode(), rowPart2.hashCode())
    }

    @Test
    fun `hashCode returns different values for non equal objects`() {
        val rowPart1 = RowPart(0, "A".toCharArray())
        val rowPart2 = RowPart(0, "B".toCharArray())

        assertNotEquals(rowPart1.hashCode(), rowPart2.hashCode())
    }
}
