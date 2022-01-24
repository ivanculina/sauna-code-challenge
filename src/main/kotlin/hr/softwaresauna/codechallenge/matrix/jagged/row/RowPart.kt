package hr.softwaresauna.codechallenge.matrix.jagged.row

data class RowPart(val start: Int, val chars: CharArray) {

    fun containsIndex(x: Int) = x in start until (start + chars.size)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RowPart

        if (start != other.start) return false
        if (!chars.contentEquals(other.chars)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start
        result = 31 * result + chars.contentHashCode()
        return result
    }

}
