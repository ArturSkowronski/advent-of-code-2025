import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

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
        return 0L
    }

//    part1(readInput("Day11_test")).println()
    part1(readInput("Day11")).println()
    part2(readInput("Day11")).println()
}
