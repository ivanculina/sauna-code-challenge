package hr.softwaresauna.codechallenge.matrix.jagged.row

class Row(val parts: Array<RowPart>) {

    fun contains(x: Int) = getOrNull(x) != null

    operator fun get(x: Int): Char {
        return getOrNull(x) ?: throw IndexOutOfBoundsException(errorMessage(x))
    }

    fun getOrNull(x: Int): Char? {
        // binary search
        var start = 0
        var end = parts.size - 1
        while (start <= end) {
            val mid = (start + end) / 2
            val part = parts[mid]
            if (x < part.start) {
                end = mid - 1
            } else {
                if (part.containsIndex(x)) {
                    return part.chars[x - part.start]
                }
                start = mid + 1
            }
        }
        return null
    }

    private fun errorMessage(x: Int) = "Invalid x $x!"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Row

        if (!parts.contentEquals(other.parts)) return false

        return true
    }

    override fun hashCode(): Int {
        return parts.contentHashCode()
    }
}
