enum class Direction(val sign: Int) {
    LEFT(-1),
    RIGHT(1)
};


fun main() {
    data class Rotation(val direction: Direction, val degrees: Int)

    fun parse(input: String): Rotation {
        val dir = when (input[0]) {
            'L' -> Direction.LEFT
            'R' -> Direction.RIGHT
            else -> throw IllegalArgumentException("Invalid direction")
        }
        val degrees = input.substring(1).toInt()
        return Rotation(dir, degrees)
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.map { parse(it) }
            .fold(50) { acc, rotation ->
                val e = acc + (rotation.direction.sign * rotation.degrees)
                val r = ((e % 100) + 100) % 100
                if (r == 0) result++
                r
            }

        return result
    }

    fun ticks(start: Int, sign: Int, degrees: Int): Int {
        val r = (-sign * start) % 100
        val target = (r + 100) % 100
        val tick = if (target == 0) 100 else target
        if (tick > degrees) return 0
        return 1 + (degrees - tick) / 100
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.filter { it.isNotBlank() }
            .map { parse(it) }
            .fold(50) { acc, rotation ->
                result += ticks(acc, rotation.direction.sign, rotation.degrees)
                val e = acc + (rotation.direction.sign * rotation.degrees)
                ((e % 100) + 100) % 100
            }
        return result
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("L1")) == R)

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(readInput("Day01")).println()
//    part1(input).println()
    part2(readInput("Day01")).println()
}
