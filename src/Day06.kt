fun main() {

    data class InputD6(val numbers: List<List<Int>>, val operations: List<String>)

    fun parse(input: List<String>): InputD6 {
        val numbers = input.take(3).map {
            it
                .replace("     ", " ")
                .replace("    ", " ")
                .replace("   ", " ")
                .replace("  ", " ")
                .split(" ").map { it.toInt() }
        }
        val operations = input[3]
            .replace("     ", " ")
            .replace("    ", " ")
            .replace("   ", " ")
            .replace("  ", " ")
            .split(" ")


        return InputD6(
            numbers = numbers,
            operations = operations
        )
    }

    fun parse2(input: List<String>): InputD6 {
        val chunks = input.take(4)
            .map { it.trim().split(Regex("\\s+")) }
            .let { rows ->
                val max = rows.maxOf { it.size }
                rows.map { row -> row + List(max - row.size) { "" } }
            }

        val mappedChunks = chunks[0].indices.map { i ->
            chunks.map { it[i] }
        }

        val finalChunks = mappedChunks.map { chunk ->
            val number0 = listOf(chunk[0].getOrNull(0), chunk[1].getOrNull(0), chunk[2].getOrNull(0)).filter { it != null }.joinToString("") { it.toString().trim() }.toInt()
            val number1 = listOf(chunk[0].getOrNull(1), chunk[1].getOrNull(1), chunk[2].getOrNull(1)).filter { it != null }.joinToString("") { it.toString().trim() }.toInt()
            val number2 = listOf(chunk[0].getOrNull(2), chunk[1].getOrNull(2), chunk[2].getOrNull(2)).filter { it != null }.joinToString("") { it.toString().trim() }.toInt()
            val number3 = listOf(chunk[0].getOrNull(3), chunk[1].getOrNull(3), chunk[2].getOrNull(3)).filter { it != null }.joinToString("") { it.toString().trim() }.toInt()
            listOf(number0, number1, number2, number3)
        }

        println(finalChunks)


        val operations = input[4]
            .replace("     ", " ")
            .replace("    ", " ")
            .replace("   ", " ")
            .replace("  ", " ")
            .split(" ")


        return InputD6(
            numbers = finalChunks,
            operations = operations
        )
    }

    fun part1(input: List<String>): Long {
        val parsed = parse(input)
        return parsed.operations.mapIndexed { index, it ->
            if (it == "*") {
                1L * parsed.numbers[0].getOrElse(index, { 0 }) *
                        parsed.numbers[1].getOrElse(index, { 0 }) *
                        parsed.numbers[2].getOrElse(index, { 0 }) *
                        parsed.numbers[3].getOrElse(index, { 0 })

            } else if (it == "+") {
                1L * parsed.numbers[0].getOrElse(index, { 0 }) +
                        parsed.numbers[1].getOrElse(index, { 0 }) +
                        parsed.numbers[2].getOrElse(index, { 0 }) +
                        parsed.numbers[3].getOrElse(index, { 0 })
            } else {
                0L
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val parsed = parse2(input)
        return parsed.operations.mapIndexed { index, it ->
            if (it == "*") {
                1L * parsed.numbers[index].getOrElse(0, { 0 }) *
                        parsed.numbers[index].getOrElse(1, { 0 }) *
                        parsed.numbers[index].getOrElse(2, { 0 }) *
                        parsed.numbers[index].getOrElse(3, { 0 })

            } else if (it == "+") {
                1L * parsed.numbers[index].getOrElse(0, { 0 }) +
                        parsed.numbers[index].getOrElse(1, { 0 }) +
                        parsed.numbers[index].getOrElse(2, { 0 }) +
                        parsed.numbers[index].getOrElse(3, { 0 })
            } else {
                0L
            }
        }.sum()
    }


//    part1(readInput("Day06")).println()
    part2(readInput("Day06")).println()
}
