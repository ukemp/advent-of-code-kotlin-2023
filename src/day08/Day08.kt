package day08

import println
import readInput

fun parseMap(input: List<String>): Map<String, Pair<String, String>> {
    val regex = Regex("""([A-Z]{3}), ([A-Z]{3})""")
    return input.filter { it.contains("=") }.associate { line ->
        val (key, pairs) = line.split(" = ")
        val group = regex.find(pairs)!!.groupValues
        key to (group[1] to group[2])
    }
}

private fun gcd(a: Long, b: Long): Long {
    var a = a
    var b = b
    while (b > 0) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}

private fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}

private fun lcm(input: List<Long>): Long {
    var result = input[0]
    for (i in 1 until input.size) result = lcm(result, input[i])
    return result
}

fun main() {

    fun countSteps(
        instructions: String,
        map: Map<String, Pair<String, String>>,
        start: String,
        isTerminated: (String) -> Boolean
    ): Long {
        var count = 0L
        var src = start
        outer@ while (true) {
            for (instruction in instructions) {
                when (instruction) {
                    'L' -> src = map[src]!!.first
                    'R' -> src = map[src]!!.second
                }
                count++
                if (isTerminated(src)) {
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
        val sources = map.filter { entry -> entry.key.endsWith("A") }.map { entry -> entry.key }
        val counts = sources.map { source ->
            countSteps(input[0], map, source) { it.endsWith("Z") }
        }

        return lcm(counts)
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day08_test")) == 2L)
    check(part2(readInput("Day08_test2")) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
