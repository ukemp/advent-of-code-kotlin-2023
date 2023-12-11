package day11
import Coordinate
import println
import readInput


fun computeResult(input: List<String>, expansion: Int): Long {
    val universe = input.foldIndexed(mutableListOf<Coordinate>()) { y, acc, line ->
        line.forEachIndexed { x, c -> if (c == '#') acc.add(Coordinate(x, y)) }
        acc
    }
    val emptyColumns = (0 until input[0].length).filter { x -> universe.firstOrNull { c -> c.x == x } == null }
    val emptyRows = input.indices.filter { y -> universe.firstOrNull { c -> c.y == y } == null }

    emptyColumns.forEachIndexed { offset, x ->
        for (index in universe.indices) {
            if (universe[index].x > x + (offset * (expansion - 1))) {
                universe[index] = universe[index].moveBy(dx = expansion - 1)
            }
        }
    }
    emptyRows.forEachIndexed { offset, y ->
        for (index in universe.indices) {
            if (universe[index].y > y + (offset * (expansion - 1))) {
                universe[index] = universe[index].moveBy(dy = expansion - 1)
            }
        }
    }
    return universe.indices.sumOf { index ->
        (index until universe.size).sumOf { j ->
            universe[index].distanceTo(universe[j])
        }
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        return computeResult(input, 2)
    }

    fun part2(input: List<String>, expansion: Int): Long {
        return computeResult(input, expansion)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput).also(::println) == 374L)
    check(part2(testInput, 10).also(::println) == 1030L)
    check(part2(testInput, 100).also(::println) == 8410L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input, 1_000_000).println()
}
