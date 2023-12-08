package day08

import println
import readInput

fun parseMap(input: List<String>): Map<String, Instruction> {
    val regex = Regex("""([A-Z]{3}), ([A-Z]{3})""")
    return input.filter { it.contains("=") }.associate { line ->
        val (key, pairs) = line.split(" = ")
        val group = regex.find(pairs)!!.groupValues
        key to Instruction(group[1], group[2])
    }
}

data class Instruction(val left: String, val right: String) {

    fun nodeFor(instr: Char): String {
        return when (instr) {
            'L' -> left
            'R' -> right
            else -> throw Error()
        }
    }
}

private fun greatestCommonDivisor(a0: Long, b0: Long): Long {
    var a = a0
    var b = b0
    while (b > 0) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}

private fun leastCommonMultiple(a: Long, b: Long): Long {
    return a * (b / greatestCommonDivisor(a, b))
}

private fun leastCommonMultiple(input: List<Long>): Long {
    var result = input[0]
    for (i in 1 until input.size) result = leastCommonMultiple(result, input[i])
    return result
}

fun main() {

    fun countSteps(
        instructions: String,
        map: Map<String, Instruction>,
        start: String,
        isTerminated: (String) -> Boolean
    ): Long {
        var count = 0L
        var node = start
        outer@ while (true) {
            for (instruction in instructions) {
                node = map[node]?.nodeFor(instruction) ?: throw Error("Missing '$node'")
                count++
                if (isTerminated(node)) {
                    break@outer
                }
            }
        }
        return count
    }

    fun part1(input: List<String>): Long {
        return countSteps(input[0], parseMap(input), "AAA") { it == "ZZZ" }
    }

    fun part2(input: List<String>): Long {
        val map = parseMap(input)
        val startNodes = map.filter { entry -> entry.key.endsWith("A") }.map { entry -> entry.key }
        val counts = startNodes.map { node ->
            countSteps(input[0], map, node) { it.endsWith("Z") }
        }

        return leastCommonMultiple(counts)
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day08_test")) == 2L)
    check(part2(readInput("Day08_test2")) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
