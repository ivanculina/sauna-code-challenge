package hr.softwaresauna.codechallenge

data class Position(val x: Int, val y: Int) {

    fun up() = Position(x, y - 1)

    fun down() = Position(x, y + 1)

    fun left() = Position(x - 1, y)

    fun right() = Position(x + 1, y)

}
