package day05

import println
import readInput
import kotlin.math.min

data class Mapping(val source: LongRange, val destination: LongRange) {

    fun mapSeed(seed: Long): Long {
        return if (source.contains(seed)) {
            destination.first + (seed - source.first)
        } else {
            seed
        }
    }
}

data class Almanac(val seeds: List<Long>, val mappings: List<List<Mapping>>) {

    fun locationOf(seed: Long): Long {
        var current = seed
        mappings.forEach { mapping ->
            current = mapping.firstOrNull { map -> map.source.contains(current) }?.mapSeed(current) ?: current
        }
        return current
    }
}

fun parseAlamanac(input: List<String>): Almanac {
    val seeds = input[0].substringAfter(": ").split(" ").map { it.trim().toLong() }
    val mappings = mutableListOf<List<Mapping>>()

    val iterator = input.iterator()
    while(iterator.hasNext()) {
        val line = iterator.next()
        if (line.endsWith("map:")) { // we don't need the names, as the list is sorted correctly
            val mapping = mutableListOf<Mapping>()
            var mapStr: String
            while(iterator.hasNext()) {
                mapStr = iterator.next()
                if (mapStr.isBlank()) {
                    break
                }
                val (destStart, srcStart, range) = mapStr.split(" ").map { it.toLong() }
                mapping.add(Mapping(srcStart until srcStart + range, destStart until destStart + range))
            }
            mappings.add(mapping)
        }
    }

    return Almanac(seeds, mappings)
}

fun main() {
    fun part1(input: List<String>): Long {
        val almanac = parseAlamanac(input)
        return almanac.seeds.minOf { almanac.locationOf(it) }
    }

    fun part2(input: List<String>): Long {
        val almanac = parseAlamanac(input)
        var min = Long.MAX_VALUE
        for (i in 0 until almanac.seeds.size step 2) {
            for (seed in almanac.seeds[i] until almanac.seeds[i] + almanac.seeds[i + 1]) {
                val location = almanac.locationOf(seed)
                min = min(location, min)
            }
            println("Done: ${almanac.seeds[i]}")
        }
        return min
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
