package day11
import Coordinate
import println
import readInput


fun computeResult(input: List<String>, expansion: Int): Long {
    val universe = input.foldIndexed(mutableListOf<Coordinate>()) { y, acc, line ->
        line.forEachIndexed { x, c -> if (c == '#') acc.add(Coordinate(x, y)) }
        acc
    }
    val emptyColumns = (0 ..<input[0].length).filter { x -> universe.firstOrNull { c -> c.x == x } == null }
    val emptyRows = input.indices.filter { y -> universe.firstOrNull { c -> c.y == y } == null }

    // Expand the universe
    emptyColumns.forEachIndexed { offset, x ->
        for (i in universe.indices) {
            if (universe[i].x > x + (offset * expansion)) {
                universe[i] = universe[i].moveBy(dx = expansion)
            }
        }
    }
    emptyRows.forEachIndexed { offset, y ->
        for (i in universe.indices) {
            if (universe[i].y > y + (offset * expansion)) {
                universe[i] = universe[i].moveBy(dy = expansion)
            }
        }
    }

    // Compute all possible distances
    return universe.indices.sumOf { index ->
        (index ..<universe.size).sumOf { j ->
            universe[index].distanceTo(universe[j])
        }
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        return computeResult(input, 1)
    }

    fun part2(input: List<String>, expansion: Int): Long {
        return computeResult(input, expansion)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput).also(::println) == 374L)
    check(part2(testInput, 9).also(::println) == 1030L)
    check(part2(testInput, 99).also(::println) == 8410L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input, 999_999).println()
}
