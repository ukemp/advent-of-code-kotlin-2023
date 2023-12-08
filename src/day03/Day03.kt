package day03

import println
import readInput

data class PartNumber(val y: Int, val x0: Int, val x1: Int, val value: Int)

fun List<String>.neighboursOf(part: PartNumber): Sequence<Pair<Int, Int>> = sequence {
    // Go left first
    if (part.x0 > 0) {
        yield(part.x0 - 1 to part.y)
    }
    // Take the lower row
    if (part.y < this@neighboursOf.size - 1) {
        if (part.x0 > 0) {
            yield(part.x0 - 1 to part.y + 1)
        }
        for (x in part.x0..part.x1) {
            yield(x to part.y + 1)
        }
        if (part.x1 < this@neighboursOf.size - 1) {
            yield(part.x1 + 1 to part.y + 1)
        }
    }
    // Go right
    if (part.x1 < this@neighboursOf[part.y].length - 1) {
        yield(part.x1 + 1 to part.y)
    }
    // Take the upper row
    if (part.y > 0) {
        if (part.x0 > 0) {
            yield(part.x0 - 1 to part.y - 1)
        }
        for (x in part.x0..part.x1) {
            yield(x to part.y - 1)
        }
        if (part.x1 < this@neighboursOf.size - 1) {
            yield(part.x1 + 1 to part.y - 1)
        }
    }
}

fun parsePartNumbers(input: List<String>): List<PartNumber> {
    val result = mutableListOf<PartNumber>()

    input.forEachIndexed { y, line ->
        var x0 = -1
        var x1 = -1
        line.forEachIndexed { x, char ->
            if (char.isDigit()) {
                if (x0 == -1) {
                    x0 = x
                } else {
                    x1 = x
                }
            } else if (x0 != -1) {
                if (x1 == -1) {
                    x1 = x0
                }
                result += PartNumber(y, x0, x1, line.substring(x0, x1 + 1).toInt())
                x0 = -1
                x1 = -1
            }
        }
        if (x0 != -1) {
            if (x1 == -1) {
                x1 = x0
            }
            result += PartNumber(y, x0, x1, line.substring(x0, x1 + 1).toInt())
        }
    }
    return result
}

fun main() {

    fun part1(input: List<String>): Int {
        val partNumbers = parsePartNumbers(input)

        return partNumbers.filter { partNumber ->
            input.neighboursOf(partNumber).firstOrNull { input[it.second][it.first] != '.' } != null
        }.sumOf { partNumber -> partNumber.value }
    }

    fun part2(input: List<String>): Int {
        val partNumbers = parsePartNumbers(input)
        var sum = 0
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '*') {
                    val star = x to y
                    val candidates = partNumbers.filter { partNumber ->
                        input.neighboursOf(partNumber).firstOrNull { it == star } != null
                    }
                    if (candidates.size == 2) {
                        sum += (candidates[0].value * candidates[1].value)
                    }
                }
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
