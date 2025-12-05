fun main() {

    data class InputD5(val ingredients: List<Long>, val ranges: List<Pair<Long, Long>>)

    fun parse(input: List<String>): InputD5 {
        val indexOfFirst = input.indexOfFirst { it.isEmpty() }

        return InputD5(
            ingredients = input.drop(indexOfFirst + 1).map { it.toLong() },
            ranges = input.take(indexOfFirst).map {
                Pair(it.split("-")[0].toLong(), it.split("-")[1].toLong())
            }
        )
    }

    fun part1(input: List<String>): Int {
        val parsed = parse(input)
        val result = parsed.ingredients.count { ingredient ->
            parsed.ranges.any { (it.first..it.second).contains(ingredient) }
        }

        return result
    }

    fun part2(input: List<String>): Long {
        val parsed = parse(input)

        val sorted = parsed.ranges
            .map { (s, e) -> Pair(s, e) }
            .sortedWith(compareBy<Pair<Long, Long>> { it.first }.thenBy { it.second })

        var result = 0L
        var currentStart = sorted[0].first
        var currentEnd = sorted[0].second

        sorted.drop(1).forEach {
            if (it.first <= currentEnd + 1) {
                if (it.second > currentEnd) currentEnd = it.second
            } else {
                result += currentEnd - currentStart + 1
                currentStart = it.first
                currentEnd = it.second
            }
        }
        result += currentEnd - currentStart + 1

        return result
    }


    part1(readInput("Day05")).println()
    part2(readInput("Day05")).println()
}
