fun main() {

    fun parse(input: List<String>): Map<String, List<String>> {
        return input.filter { it.isNotBlank() }
            .associate { line ->
                val (node, connections) = line.split(": ")
                node to connections.split(" ")
            }
    }

    fun part1(input: List<String>): Long {
        val graph = parse(input)
        val memo = mutableMapOf<String, Long>()

        fun countPaths(node: String): Long {
            if (node == "out") return 1L
            if (node !in graph) return 0L
            memo[node]?.let { return it }

            val count = graph[node]!!.sumOf { countPaths(it) }
            memo[node] = count
            return count
        }

        return countPaths("you")
    }

    fun part2(input: List<String>): Long {
        val graph = parse(input)

        val state = mutableMapOf<Pair<String, Int>, Long>()

        fun countPaths(node: String, visited: Int): Long {
            val newVisited = when (node) {
                "dac" -> visited or 1
                "fft" -> visited or 2
                else -> visited
            }

            if (node == "out") {
                return if (newVisited == 3) 1L else 0L
            }
            if (node !in graph) return 0L

            val key = node to newVisited
            state[key]?.let { return it }

            val count = graph[node]!!.sumOf { countPaths(it, newVisited) }
            state[key] = count
            return count
        }

        return countPaths("svr", 0)
    }

//    part1(readInput("Day11_test")).println()
    part1(readInput("Day11")).println()
    part2(readInput("Day11")).println()
}
