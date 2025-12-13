fun main() {

    fun parse(input: String): List<Pair<Long, Long>> {
        return input.split(",").map {
            Pair(it.split("-")[0].toLong(), it.split("-")[1].toLong())
        }
    }

    fun part1(input: String): Long {
        val parse = parse(input)
        return parse.sumOf {
            println(it)
            (it.first..it.second).sumOf { number ->
                val length = number.toString().length
                if (length % 2 == 0) {
                    if(number.toString().substring(0, length / 2) == number.toString().substring(length / 2)) {
                        println(number.toString())
                        number.toString().toLong()
                    } else {
                        0
                    }
                } else {
                    0
                }
            }
        }
    }

    fun invalid(s: String): Boolean {
        val n = s.length
        for (p in 1..n / 2) {
            if (n % p == 0) {
                if (s.take(p).repeat(n / p) == s) return true
            }
        }
        return false
    }

    fun part2(input: String): Long {
        val parse = parse(input)
        return parse.sumOf { (start, end) ->
            (start..end).sumOf { number ->
                if (invalid(number.toString())) number else 0L
            }
        }
    }


    part1(readInput("Day02")[0]).println()
    part2(readInput("Day02")[0]).println()
}
