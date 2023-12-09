package day09

import println
import readInput


fun buildDifferences(line: String): List<List<Long>> {
    val history = line.split(" ").map { it.toLong() }

    return buildList {
        add(history)

        do {
            val current = last()
            val next = current.foldIndexed(mutableListOf<Long>()) { index, acc, l ->
                if (index > 0) {
                    acc.add(l - current[index - 1])
                }
                acc
            }
            add(next)

        } while (!next.all { it == 0L })
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            buildDifferences(line).map { it.last() }.foldRight(0L) { acc, l ->
                l - acc
            } * -1
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            buildDifferences(line).map { it.first() }.foldRight(0L) { acc, l ->
                acc - l
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114L)
    check(part2(testInput) == 2L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
