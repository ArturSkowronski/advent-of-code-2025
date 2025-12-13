import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun main() {
    data class Machine(
        val target: List<Boolean>,
        val buttons: List<Set<Int>>
    )

    fun parse(input: List<String>): List<Machine> {
        return input.map { line ->
            val bracketMatch = Regex("""\[([.#]+)]""").find(line)!!
            val pattern = bracketMatch.groupValues[1]
            val target = pattern.map { it == '#' }

            val buttonMatches = Regex("""\(([0-9,]+)\)""").findAll(line)
            val buttons = buttonMatches.map { match ->
                match.groupValues[1].split(",").map { it.toInt() }.toSet()
            }.toList()

            Machine(target, buttons)
        }
    }

    fun solveMachine(machine: Machine): Int {
        val numLights = machine.target.size
        val numButtons = machine.buttons.size

        var minPresses = Int.MAX_VALUE
        val totalCombinations = 2.0.pow(numButtons).toInt()

        for (mask in 0 until totalCombinations) {
            val lights = BooleanArray(numLights) { false }

            val pressedButtons = mutableListOf<Int>()
            var tempMask = mask
            for (b in 0 until numButtons) {
                if (tempMask % 2 == 1) {
                    pressedButtons.add(b)
                }
                tempMask /= 2
            }

            val presses = pressedButtons.size

            if (presses >= minPresses) continue

            for (b in pressedButtons) {
                for (lightIdx in machine.buttons[b]) {
                    if (lightIdx < numLights) {
                        lights[lightIdx] = !lights[lightIdx]
                    }
                }
            }

            if (lights.indices.all { lights[it] == machine.target[it] }) {
                minPresses = presses
            }
        }

        return if (minPresses == Int.MAX_VALUE) 0 else minPresses
    }

    fun part1(input: List<String>): Long {
        val machines = parse(input)
        return machines.sumOf { solveMachine(it).toLong() }
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    part1(readInput("Day10_test")).println()
    part1(readInput("Day10")).println()
    part2(readInput("Day10")).println()
}