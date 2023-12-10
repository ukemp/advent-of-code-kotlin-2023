package day10
import Coordinate
import println
import readInput

data class Tile(val label: Char, val position: Coordinate) {

    fun next(): Pair<Coordinate, Coordinate> {
        return when (label) {
            '|' -> position.moveBy(dy = -1) to position.moveBy(dy = 1)
            '-' -> position.moveBy(dx = -1) to position.moveBy(dx = 1)
            'L' -> position.moveBy(dy = -1) to position.moveBy(dx = 1)
            'J' -> position.moveBy(dy = -1) to position.moveBy(dx = -1)
            '7' -> position.moveBy(dy = 1) to position.moveBy(dx = -1)
            'F' -> position.moveBy(dy = 1) to position.moveBy(dx = 1)
            else -> throw IllegalStateException()
        }
    }
}

fun Pair<Coordinate, Coordinate>.matches(coordinate: Coordinate) = first == coordinate || second == coordinate

fun parseGrid(input: List<String>): Pair<Coordinate, Map<Coordinate, Tile>> {
    var start: Coordinate? = null
    val grid = buildMap {
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == 'S') {
                    start = Coordinate(x, y)
                } else if (c != '.') {
                    val pos = Coordinate(x, y)
                    this[pos] = Tile(c, pos)
                }
            }
        }
    }

    return start!! to grid
}

fun Map<Coordinate, Tile>.next(previous: Coordinate, current: Coordinate): Coordinate {
    val (left, right) = getValue(current).next()
    return if (left != previous) {
        left
    } else if (right != previous) {
        right
    } else {
        throw Error()
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val (start, grid) = parseGrid(input)
        var (left, right) = start.neighbours().filter { c -> grid[c]?.next()?.matches(start) ?: false }
        var leftPrevious = start
        var rightPrevious = start
        var count = 1L
        while (true) {
            val nextLeft = grid.next(leftPrevious, left)
            val nextRight = grid.next(rightPrevious, right)
            count++
            if (nextLeft == nextRight) {
                break
            }
            leftPrevious = left
            rightPrevious = right
            left = nextLeft
            right = nextRight
        }
        return count
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 8L)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
