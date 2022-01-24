package hr.softwaresauna.codechallenge.traveler

import hr.softwaresauna.codechallenge.Position
import hr.softwaresauna.codechallenge.exception.*
import hr.softwaresauna.codechallenge.matrix.Matrix

class MovementResolver(private val matrix: Matrix) {

    fun isTurnNeeded(position: Position, direction: Direction): Boolean {
        return isDirectionDownAndNotPossible(direction, position) ||
                isDirectionUpAndNotPossible(direction, position) ||
                isDirectionRightAndNotPossible(direction, position) ||
                isDirectionLeftAndNotPossible(direction, position)
    }

    fun getNewPosition(position: Position, direction: Direction): Position {
        return if (isDirectionUpAndPossible(direction, position)) {
            position.up()
        } else if (isDirectionRightAndPossible(direction, position)) {
            position.right()
        } else if (isDirectionDownAndPossible(direction, position)) {
            position.down()
        } else if (isDirectionLeftAndPossible(direction, position)) {
            position.left()
        } else {
            throw BrokenPathException(brokenPathExceptionMessage(position))
        }
    }

    fun getInitDirection(position: Position): Direction {
        val possibleDirections = mutableSetOf<Direction>()
        if (matrix.contains(position.up())) {
            possibleDirections.add(Direction.UP)
        }
        if (matrix.contains(position.right())) {
            possibleDirections.add(Direction.RIGHT)
        }
        if (matrix.contains(position.down())) {
            possibleDirections.add(Direction.DOWN)
        }
        if (matrix.contains(position.left())) {
            possibleDirections.add(Direction.LEFT)
        }
        if (possibleDirections.size > 1) {
            throw MultipleStartingPathsException()
        } else if (possibleDirections.isEmpty()) {
            throw NoStartingPathException()
        }
        return possibleDirections.first()
    }

    fun getNewDirection(position: Position, current: Direction): Direction {
        fun forksExceptionMessage() = "Forks at $position!"
        fun fakeTurnMessage() = "Fake turn at $position!"

        fun getNewVerticalDirection(): Direction {
            val upChar = matrix.getOrNull(position.up())
            val downChar = matrix.getOrNull(position.down())
            return if (upChar != null && downChar == null) {
                Direction.UP
            } else if (downChar != null && upChar == null) {
                Direction.DOWN
            } else if (upChar != null && downChar != null) {
                throw ForksException(forksExceptionMessage())
            } else {
                throw FakeTurnException(fakeTurnMessage())
            }
        }

        fun getNewHorizontalDirection(): Direction {
            val rightChar = matrix.getOrNull(position.right())
            val leftChar = matrix.getOrNull(position.left())
            return if (rightChar != null && leftChar == null) {
                Direction.RIGHT
            } else if (leftChar != null && rightChar == null) {
                Direction.LEFT
            } else if (rightChar != null && leftChar != null) {
                throw ForksException(forksExceptionMessage())
            } else {
                throw FakeTurnException(fakeTurnMessage())
            }
        }

        return when (current) {
            Direction.RIGHT, Direction.LEFT -> getNewVerticalDirection()
            Direction.UP, Direction.DOWN -> getNewHorizontalDirection()
        }
    }

    private fun brokenPathExceptionMessage(position: Position) = "Broken path at $position!"

    private fun isDirectionUpAndPossible(direction: Direction, position: Position) =
        direction == Direction.UP && matrix.contains(position.up())

    private fun isDirectionDownAndPossible(direction: Direction, position: Position) =
        direction == Direction.DOWN && matrix.contains(position.down())

    private fun isDirectionRightAndPossible(direction: Direction, position: Position) =
        direction == Direction.RIGHT && matrix.contains(position.right())

    private fun isDirectionLeftAndPossible(direction: Direction, position: Position) =
        direction == Direction.LEFT && matrix.contains(position.left())

    private fun isDirectionDownAndNotPossible(direction: Direction, position: Position) =
        direction == Direction.DOWN && !matrix.contains(position.down())

    private fun isDirectionUpAndNotPossible(direction: Direction, position: Position) =
        direction == Direction.UP && !matrix.contains(position.up())

    private fun isDirectionRightAndNotPossible(direction: Direction, position: Position) =
        direction == Direction.RIGHT && !matrix.contains(position.right())

    private fun isDirectionLeftAndNotPossible(direction: Direction, position: Position) =
        direction == Direction.LEFT && !matrix.contains(position.left())

}
