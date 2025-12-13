fun main() {

    fun parse(input: List<String>): List<List<Int>> {
        return input.map { i -> i.toCharArray().map { c -> c.digitToInt() } }
    }

    fun part1(input: List<String>): Int {
        val parse = parse(input)

        val joltages = parse.mapIndexed { index, i ->
            val maxes = i.dropLast(1).max()
            val max = i.dropWhile { it != maxes }.drop(1)
            maxes * 10 + max.max()
        }

        return joltages.sum()
    }


    fun part2(input: List<String>): Long {
        val parse = parse(input)

        val joltages = parse.map { joltages ->
            var drop = 11
            var rest = joltages
            val maxString = mutableListOf<String>()
            while(drop >= 0){
                val maxes = rest.dropLast(drop).max()
                maxString.add(maxes.toString())
                rest = rest.dropWhile { it != maxes }.drop(1)
                drop--
                if (rest.size < drop) {
                    maxString.add(rest.joinToString(""))
                    drop = 0
                }
            }
            maxString.joinToString ("").toLong()
        }

        return joltages.sum()
    }

    part1(readInput("Day03")).println()
    part2(readInput("Day03")).println()
}
