package hr.softwaresauna.codechallenge.matrix

import hr.softwaresauna.codechallenge.Position

interface Matrix {

    fun contains(position: Position): Boolean

    operator fun get(position: Position): Char

    fun getOrNull(position: Position): Char?

}
