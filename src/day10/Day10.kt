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
        val (start, grid) = parseGrid(input)
        var (left, right) = start.neighbours().filter { c -> grid[c]?.next()?.matches(start) ?: false }
        var leftPrevious = start
        var rightPrevious = start
        val coordinates = mutableListOf(start)
        while (true) {
            val nextLeft = grid.next(leftPrevious, left)
            val nextRight = grid.next(rightPrevious, right)
            coordinates.add(nextLeft)
            coordinates.add(nextRight)
            if (nextLeft == nextRight) {
                break
            }
            leftPrevious = left
            rightPrevious = right
            left = nextLeft
            right = nextRight
        }
        val xmin = coordinates.minOf { c -> c.x }
        val xmax = coordinates.maxOf { c -> c.x }
        val ymin = coordinates.minOf { c -> c.y }
        val ymax = coordinates.maxOf { c -> c.y }

        var count = 0L
        for (x in xmin..xmax) {
            for (y in ymin..ymax) {
                val candidate = Coordinate(x, y)
                if (!coordinates.contains(candidate)) {
                    val intersections = (candidate.x + 1..xmax).count { x0 ->
                        val label = grid[Coordinate(x0, y)]?.label
                        label != null && label != '-'
                    }
                    println(intersections)
                    if (intersections % 2 != 0) {
                        count++
                    }
                }
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day10_test")) == 8L)
    check(part2(readInput("Day10_test2")).also(::println) == 10L)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
