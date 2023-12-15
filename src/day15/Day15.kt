package day15

import println
import readInput


fun hashOf(string: String): Int {
    return string.fold(0) { acc, c ->
        ((acc + c.code) * 17) % 256
    }
}

class Box(private val index: Int, private val lenses: MutableList<String> = mutableListOf()) {

    fun addLens(lens: String, focal: Int) {
        val index = lenses.indexOfFirst { it.startsWith(lens) }
        if (index == -1) {
            lenses.add("$lens $focal")
        } else {
            lenses[index] = "$lens $focal"
        }
    }

    fun removeLens(lens: String) {
        lenses.removeAll { label ->
            label.startsWith(lens)
        }
    }

    fun focusingPower(): Long {
        return lenses.foldIndexed(0L) { lensIndex, acc, lens ->
            acc + ((index + 1) * (lensIndex + 1) * lens.substringAfter(' ').toLong())
        }
    }

    override fun toString(): String {
        return "Box $index: ${lenses.joinToString(separator = "] [", prefix = "[", postfix = "]")}"
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input[0].split(",").sumOf { hashOf(it) }
    }

    fun part2(input: List<String>): Long {
        val boxes = List(256) { Box(it) }
        input[0].split(",").forEach {
            if (it.endsWith('-')) {
                val label = it.substring(0, it.length - 1)
                boxes[hashOf(label)].removeLens(label)
            } else {
                val label = it.substringBefore('=')
                val focal = it.substringAfter('=').toInt()
                boxes[hashOf(label)].addLens(label, focal)
            }
        }
        return boxes.sumOf { it.focusingPower() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145L)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
