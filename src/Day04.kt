
data class Card(val id: Int, val winning: List<Int>, val own: List<Int>) {

    val wins: Int = own.intersect(winning.toSet()).size
}

fun parseCards(input: List<String>): List<Card> {
    return input.map { line ->
        val (first, second) = line.split(":")
        val id = first.substringAfter(" ").trim().toInt()
        val (left, right) = second.split("|").map { it.trim().split(Regex("""\s+""")) }
        Card(id, left.map { it.toInt() }, right.map { it.toInt() })
    }
}


fun main() {

    fun part1(input: List<String>): Int {
        val cards = parseCards(input)
        return cards.sumOf { card -> 1.shl(card.wins - 1) }
    }

    fun part2(input: List<String>): Long {
        val cards = parseCards(input)
        val instances = mutableMapOf<Int, Long>()

        cards.forEach { card -> instances[card.id] = 1 }
        cards.forEach { card ->
            val copies = instances[card.id]!!
            for (id in card.id + 1..card.id + card.wins) {
                instances[id] = instances[id]!! + copies
            }
        }
        return instances.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30L)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
