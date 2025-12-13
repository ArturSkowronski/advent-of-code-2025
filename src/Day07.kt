fun main() {

    fun parse(input: List<String>): List<CharArray> {
        return input.map { it.toCharArray() }
    }

    fun part1(input: List<String>): Int {
        val grid = parse(input)
        val startY = grid[0].indexOfFirst { it == 'S' }
        var currentRoots = mutableSetOf(Pair(0, startY))
        var splits = 0

        for (currentX in 1 until grid.size) {
            val newRoots = mutableSetOf<Pair<Int, Int>>()
            for (root in currentRoots) {
                if (grid[currentX][root.second] == '^') {
                    splits++
                    if (root.second - 1 >= 0) newRoots.add(Pair(currentX, root.second - 1))
                    if (root.second + 1 < grid[currentX].size) newRoots.add(Pair(currentX, root.second + 1))
                } else {
                    newRoots.add(Pair(currentX, root.second))
                }
            }
            currentRoots = newRoots
        }

        return splits
    }



    fun part2(input: List<String>): Long {
        val grid = parse(input)
        val startY = grid[0].indexOfFirst { it == 'S' }
        var beams = mutableMapOf(startY to 1L)

        for (currentX in 1 until grid.size) {
            val newBeams = mutableMapOf<Int, Long>()
            for ((y, count) in beams) {
                if (grid[currentX][y] == '^') {
                    if (y - 1 >= 0) newBeams[y - 1] = (newBeams[y - 1] ?: 0) + count
                    if (y + 1 < grid[currentX].size) newBeams[y + 1] = (newBeams[y + 1] ?: 0) + count
                } else {
                    newBeams[y] = (newBeams[y] ?: 0) + count
                }
            }
            beams = newBeams
        }

        return beams.values.sum()
    }

    part1(readInput("Day07")).println()
    part2(readInput("Day07")).println()
}
