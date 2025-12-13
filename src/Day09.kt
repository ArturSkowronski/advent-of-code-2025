import kotlin.math.abs
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
fun main() {

    fun parse(input: List<String>): List<Pair<Int, Int>> {
        return input.map {
            val split = it.split(",")
            Pair(split[0].toInt(), split[1].toInt())
        }
    }

    fun field(a: Pair<Int, Int>, b: Pair<Int, Int>): Long {
        val dx = (abs(a.first - b.first) + 1).toLong()
        val dy = (abs(a.second - b.second) + 1).toLong()
        return dx * dy
    }

    fun part1(input: List<String>): Long {
        val parse = parse(input).toSet()
        return parse.flatMap { el1 ->
            parse.filter { el -> el != el1 }
                .map { el -> field(el1, el) }
        }.max()

    }

    fun part2(input: List<String>): Long {
        val redTiles = parse(input)
        val redSet = redTiles.toSet()

        val edgeTiles = mutableSetOf<Pair<Int, Int>>()
        for (i in redTiles.indices) {
            val cur = redTiles[i]
            val next = redTiles[(i + 1) % redTiles.size]

            if (cur.first == next.first) {
                for (y in min(cur.second, next.second)..max(cur.second, next.second)) {
                    edgeTiles.add(Pair(cur.first, y))
                }
            } else {
                for (x in min(cur.first, next.first)..max(cur.first, next.first)) {
                    edgeTiles.add(Pair(x, cur.second))
                }
            }
        }

        val validTiles = mutableSetOf<Pair<Int, Int>>()
        validTiles.addAll(edgeTiles)

        val minX = redTiles.minOf { it.first }
        val maxX = redTiles.maxOf { it.first }
        val minY = redTiles.minOf { it.second }
        val maxY = redTiles.maxOf { it.second }

        data class VSeg(val x: Int, val yMin: Int, val yMax: Int)
        val vSegs = mutableListOf<VSeg>()
        for (i in redTiles.indices) {
            val cur = redTiles[i]
            val next = redTiles[(i + 1) % redTiles.size]
            if (cur.first == next.first) {
                vSegs.add(VSeg(cur.first, min(cur.second, next.second), max(cur.second, next.second)))
            }
        }

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                if (Pair(x, y) in edgeTiles) continue

                var crossings = 0
                for (seg in vSegs) {
                    if (seg.x > x && y >= seg.yMin && y < seg.yMax) {
                        crossings++
                    }
                }
                if (crossings % 2 == 1) {
                    validTiles.add(Pair(x, y))
                }
            }
        }

        var maxArea = 0L
        for (i in redTiles.indices) {
            for (j in i + 1 until redTiles.size) {
                val a = redTiles[i]
                val b = redTiles[j]

                val x1 = min(a.first, b.first)
                val x2 = max(a.first, b.first)
                val y1 = min(a.second, b.second)
                val y2 = max(a.second, b.second)

                var allValid = true
                loop@ for (px in x1..x2) {
                    for (py in y1..y2) {
                        if (Pair(px, py) !in validTiles) {
                            allValid = false
                            break@loop
                        }
                    }
                }

                if (allValid) {
                    maxArea = max(maxArea, field(a, b))
                }
            }
        }

        return maxArea
    }

    field(Pair(2, 5), Pair(11, 1))
    part1(readInput("Day09_test")).println()
    part1(readInput("Day09")).println()
    part2(readInput("Day09")).println()
}