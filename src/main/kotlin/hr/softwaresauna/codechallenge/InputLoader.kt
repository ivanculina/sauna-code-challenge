package hr.softwaresauna.codechallenge

import hr.softwaresauna.codechallenge.exception.*
import hr.softwaresauna.codechallenge.matrix.Matrix
import hr.softwaresauna.codechallenge.matrix.jagged.JaggedMatrix
import hr.softwaresauna.codechallenge.matrix.jagged.row.Row
import hr.softwaresauna.codechallenge.matrix.jagged.row.RowPart
import java.io.File
import java.nio.file.Files

class InputLoader {

    fun load(path: String): Input {
        var startPosition: Position? = null
        var endPosition: Position? = null
        fun updateStartOrEndPosition(char: Char, x: Int, y: Int) {
            if (char == START_CHAR) {
                startPosition = getStartPosition(startPosition, x, y)
            } else if (char == END_CHAR) {
                endPosition = getEndPosition(endPosition, x, y)
            }
        }

        fun extractPartAndUpdateStartAndEnd(line: String, columnStartIndex: Int, rowIndex: Int): RowPart {
            var k = columnStartIndex
            while (k < line.length && line[k] != EMPTY_CHAR) {
                line[k].validate()
                updateStartOrEndPosition(line[k], k, rowIndex)
                k++
            }
            return RowPart(columnStartIndex, line.substring(columnStartIndex, k).toCharArray())
        }

        val rows = mutableListOf<Row>()
        getInputLines(path).forEachIndexed { i, line ->
            val rowParts = mutableListOf<RowPart>()
            var j = 0
            while (j < line.length) {
                if (line[j] != EMPTY_CHAR) {
                    val rowPart = extractPartAndUpdateStartAndEnd(line, j, i)
                    rowParts.add(rowPart)
                    j = rowPart.start + rowPart.chars.size
                }
                j++
            }
            rows.add(Row(rowParts.toTypedArray()))
        }

        if (endPosition == null) {
            throw NoEndException()
        }
        return Input(JaggedMatrix(rows.toTypedArray()), startPosition ?: throw NoStartException())
    }

    private fun Char.validate() {
        if (this !in VALID_CHARS) {
            throw InvalidCharException("Invalid char $this in the map!")
        }
    }

    private fun getInputLines(path: String): List<String> {
        val url = this::class.java.getResource(path) ?: throw IllegalArgumentException("Path $path not found!")
        return Files.readAllLines(File(url.path).toPath())
    }

    private fun getStartPosition(start: Position?, x: Int, y: Int): Position {
        if (start != null) {
            throw MultipleStartsException()
        }
        return Position(x, y)
    }

    private fun getEndPosition(end: Position?, x: Int, y: Int): Position {
        if (end != null) {
            throw MultipleEndsException()
        }
        return Position(x, y)
    }
}

data class Input(val matrix: Matrix, val start: Position)
