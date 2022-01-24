package hr.softwaresauna.codechallenge.matrix.jagged

import hr.softwaresauna.codechallenge.Position
import hr.softwaresauna.codechallenge.matrix.Matrix
import hr.softwaresauna.codechallenge.matrix.jagged.row.Row

class JaggedMatrix(private val rows: Array<Row>) : Matrix {

    override fun contains(position: Position) = getOrNull(position) != null

    override operator fun get(position: Position): Char {
        val x = position.x
        val y = position.y
        if (y !in rows.indices) {
            throw IndexOutOfBoundsException("Invalid y $y, size is ${rows.size}")
        }
        return rows[y][x]
    }

    override fun getOrNull(position: Position): Char? {
        val x = position.x
        val y = position.y
        if (y !in rows.indices) {
            return null
        }
        return rows[y].getOrNull(x)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JaggedMatrix

        if (!rows.contentEquals(other.rows)) return false

        return true
    }

    override fun hashCode(): Int {
        return rows.contentHashCode()
    }

}
