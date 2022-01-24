package hr.softwaresauna.codechallenge.traveler

import hr.softwaresauna.codechallenge.END_CHAR
import hr.softwaresauna.codechallenge.Position
import hr.softwaresauna.codechallenge.TURN_CHAR
import hr.softwaresauna.codechallenge.matrix.Matrix

class Traveler {

    private val letters = mutableListOf<Char>()
    private val positionsOfLetters = mutableSetOf<Position>()
    private val path = mutableListOf<Char>()

    fun travel(matrix: Matrix, start: Position): TravelResult {
        var position = start
        val movementResolver = MovementResolver(matrix)
        var direction = movementResolver.getInitDirection(position)
        do {
            val char = matrix[position]
            path.add(char)
            if (char.isLetter()) {
                putLetter(char, position)
            }
            if (char == TURN_CHAR || (char.isLetter() && movementResolver.isTurnNeeded(position, direction))) {
                direction = movementResolver.getNewDirection(position, direction)
            }
            position = movementResolver.getNewPosition(position, direction)
        } while (matrix[position] != END_CHAR)
        path.add(END_CHAR)
        return TravelResult(letters, path)
    }

    private fun putLetter(letter: Char, position: Position) {
        if (!positionsOfLetters.contains(position) || !letters.contains(letter)) {
            letters.add(letter)
            positionsOfLetters.add(position)
        }
    }
}

data class TravelResult(val letters: List<Char>, val path: List<Char>)
