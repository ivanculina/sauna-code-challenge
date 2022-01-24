package hr.softwaresauna.codechallenge.traveler

import hr.softwaresauna.codechallenge.Position
import hr.softwaresauna.codechallenge.exception.*
import hr.softwaresauna.codechallenge.matrix.jagged.JaggedMatrix
import hr.softwaresauna.codechallenge.matrix.jagged.row.Row
import hr.softwaresauna.codechallenge.matrix.jagged.row.RowPart
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MovementResolverTest {

    @ParameterizedTest
    @EnumSource(Direction::class)
    fun `isTurnNeeded returns true when forward not possible`(direction: Direction) {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        assertTrue { movementResolver.isTurnNeeded(Position(0, 0), direction) }
    }

    @Test
    fun `isTurnNeeded returns false when turn not needed`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('-', 'A'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        assertFalse { movementResolver.isTurnNeeded(Position(1, 0), Direction.LEFT) }
    }

    @Test
    fun `getNewPosition returns new position when direction up and possible`() {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val row2 = Row(arrayOf(RowPart(0, charArrayOf('|'))))
        val matrix = JaggedMatrix(arrayOf(row1, row2))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Position(0, 0), movementResolver.getNewPosition(Position(0, 1), Direction.UP))
    }

    @Test
    fun `getNewPosition returns new position when direction right and possible`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A', 'x'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Position(1, 0), movementResolver.getNewPosition(Position(0, 0), Direction.RIGHT))
    }

    @Test
    fun `getNewPosition returns new position when direction down and possible`() {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val row2 = Row(arrayOf(RowPart(0, charArrayOf('x'))))
        val matrix = JaggedMatrix(arrayOf(row1, row2))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Position(0, 1), movementResolver.getNewPosition(Position(0, 0), Direction.DOWN))
    }

    @Test
    fun `getNewPosition returns new position when direction left and possible`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A', '-'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Position(0, 0), movementResolver.getNewPosition(Position(1, 0), Direction.LEFT))
    }

    @ParameterizedTest
    @EnumSource(Direction::class)
    fun `getNewPosition throws when broken path`(direction: Direction) {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        val ex = assertThrows<BrokenPathException> { movementResolver.getNewPosition(Position(0, 0), direction) }
        assertEquals("Broken path at Position(x=0, y=0)!", ex.message)
    }

    @Test
    fun `getInitDirection returns up when only up possible`() {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val row2 = Row(arrayOf(RowPart(0, charArrayOf('@'))))
        val matrix = JaggedMatrix(arrayOf(row1, row2))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Direction.UP, movementResolver.getInitDirection(Position(0, 1)))
    }

    @Test
    fun `getInitDirection returns right when only right possible`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('@', 'x'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Direction.RIGHT, movementResolver.getInitDirection(Position(0, 0)))
    }

    @Test
    fun `getInitDirection returns left when only left possible`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('x', '@'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Direction.LEFT, movementResolver.getInitDirection(Position(1, 0)))
    }

    @Test
    fun `getInitDirection returns down when only down possible`() {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('@'))))
        val row2 = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val matrix = JaggedMatrix(arrayOf(row1, row2))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Direction.DOWN, movementResolver.getInitDirection(Position(0, 0)))
    }

    @Test
    fun `getInitDirection throws when multiple direction possible`() {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('|'))))
        val row2 = Row(arrayOf(RowPart(0, charArrayOf('@'))))
        val row3 = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val matrix = JaggedMatrix(arrayOf(row1, row2, row3))
        val movementResolver = MovementResolver(matrix)

        val ex = assertThrows<MultipleStartingPathsException> { movementResolver.getInitDirection(Position(0, 1)) }
        assertEquals("Multiple starting paths!", ex.message)
    }

    @Test
    fun `getInitDirection throws when no direction possible`() {
        val row = Row(arrayOf(RowPart(0, charArrayOf('@'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        val ex = assertThrows<NoStartingPathException> { movementResolver.getInitDirection(Position(0, 0)) }
        assertEquals("No starting path exception!", ex.message)
    }

    @ParameterizedTest
    @EnumSource(value = Direction::class, names = ["UP", "DOWN"])
    fun `getNewDirection returns right when old direction vertical and only right possible`(direction: Direction) {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A', 'x'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Direction.RIGHT, movementResolver.getNewDirection(Position(0, 0), direction))
    }

    @ParameterizedTest
    @EnumSource(value = Direction::class, names = ["UP", "DOWN"])
    fun `getNewDirection returns left when old direction vertical and only left possible`(direction: Direction) {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A', 'x'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Direction.LEFT, movementResolver.getNewDirection(Position(1, 0), direction))
    }

    @ParameterizedTest
    @EnumSource(value = Direction::class, names = ["LEFT", "RIGHT"])
    fun `getNewDirection returns up when old direction horizontal and only up possible`(direction: Direction) {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('x'))))
        val row2 = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val matrix = JaggedMatrix(arrayOf(row1, row2))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Direction.UP, movementResolver.getNewDirection(Position(0, 1), direction))
    }

    @ParameterizedTest
    @EnumSource(value = Direction::class, names = ["LEFT", "RIGHT"])
    fun `getNewDirection returns down when old direction horizontal and only down possible`(direction: Direction) {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('x'))))
        val row2 = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val matrix = JaggedMatrix(arrayOf(row1, row2))
        val movementResolver = MovementResolver(matrix)

        assertEquals(Direction.DOWN, movementResolver.getNewDirection(Position(0, 0), direction))
    }

    @ParameterizedTest
    @EnumSource(value = Direction::class, names = ["RIGHT", "LEFT"])
    fun `getNewDirection throws when old direction horizontal and forks`(direction: Direction) {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('x'))))
        val row2 = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val row3 = Row(arrayOf(RowPart(0, charArrayOf('|'))))
        val matrix = JaggedMatrix(arrayOf(row1, row2, row3))
        val movementResolver = MovementResolver(matrix)

        val ex = assertThrows<ForksException> { movementResolver.getNewDirection(Position(0, 1), direction) }
        assertEquals("Forks at Position(x=0, y=1)!", ex.message)
    }

    @ParameterizedTest
    @EnumSource(value = Direction::class, names = ["UP", "DOWN"])
    fun `getNewDirection throws when old direction vertical and forks`(direction: Direction) {
        val row1 = Row(arrayOf(RowPart(0, charArrayOf('x', 'A', '-'))))
        val matrix = JaggedMatrix(arrayOf(row1))
        val movementResolver = MovementResolver(matrix)

        val ex = assertThrows<ForksException> { movementResolver.getNewDirection(Position(1, 0), direction) }
        assertEquals("Forks at Position(x=1, y=0)!", ex.message)
    }

    @ParameterizedTest
    @EnumSource(Direction::class)
    fun `getNewDirection throws when fake turn`(direction: Direction) {
        val row = Row(arrayOf(RowPart(0, charArrayOf('A'))))
        val matrix = JaggedMatrix(arrayOf(row))
        val movementResolver = MovementResolver(matrix)

        val ex = assertThrows<FakeTurnException> { movementResolver.getNewDirection(Position(0, 0), direction) }
        assertEquals("Fake turn at Position(x=0, y=0)!", ex.message)
    }
}
