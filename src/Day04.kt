val directions = listOf(
    -1 to -1, -1 to 0, -1 to 1,
    0 to -1, 0 to 1,
    1 to -1, 1 to 0, 1 to 1
)

fun main() {

    fun parse(input: List<String>): List<CharArray> {
        return input.map { it.toCharArray() }
    }

    fun part1(input: List<String>): Int {
        val grid = parse(input)
        var result = 0
        val rows = grid.size
        val cols = grid[0].size

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (grid[row][col] == '@') {
                    var neighbours = 0
                    for ((x, y) in directions) {
                        val nx = row + x
                        val ny = col + y
                        if (nx in 0 until rows && ny in 0 until cols && grid[nx][ny] == '@') {
                            neighbours++
                        }
                    }

                    if (neighbours < 4) {
                        result++
                    }
                }
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val grid = parse(input)
        var result = 0
        val rows = grid.size
        val cols = grid[0].size

        while (true) {
            val toRemove = mutableListOf<Pair<Int, Int>>()
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    if (grid[row][col] == '@') {
                        var neighbours = 0
                        for ((x, y) in directions) {
                            val nx = row + x
                            val ny = col + y
                            if (nx in 0 until rows && ny in 0 until cols && grid[nx][ny] == '@') {
                                neighbours++
                            }
                        }

                        if (neighbours < 4) {
                            toRemove.add(row to col)
                        }
                    }
                }
            }

            if (toRemove.isEmpty()) {
                break
            }

            result += toRemove.size
            for ((r, c) in toRemove) {
                grid[r][c] = '.'
            }
        }

        return result
    }

    part1(readInput("Day04")).println()
    part2(readInput("Day04")).println()
}
