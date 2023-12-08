package day06

import println
import readInput

data class Race(val time: Long, val distance: Long) {

    fun computeWins(): Int {
        return (1 until time).count { speed ->
            val travelled = speed * (time - speed)
            travelled > this.distance
        }
    }
}

fun parseRace(input: List<String>): List<Race> {
    val times = input[0].substringAfter(":").trim().split(Regex("""\s+""")).map { it.toLong() }
    val distances = input[1].substringAfter(":").trim().split(Regex("""\s+""")).map { it.toLong() }

    return times.mapIndexed { index, time ->
        Race(time, distances[index])
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val races = parseRace(input).also(::println)
        val wins = races.map { race ->
            race.computeWins()
        }.reduce { acc, i ->  acc * i }

        return wins
    }

    fun part2(input: List<String>): Int {
        val race = Race(40709879L, 215105121471005L)
        return race.computeWins()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
