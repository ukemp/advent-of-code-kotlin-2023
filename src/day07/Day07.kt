package day07

import println
import readInput

// A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
data class Card(val label: Char) : Comparable<Card> {

    val value = if (label.isDigit()) {
        label.digitToInt()
    } else {
        when (label) {
            'T' -> 10
            'J' -> 11
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> throw IllegalArgumentException()
        }
    }

    override fun compareTo(other: Card): Int {
        return this.value.compareTo(other.value)
    }
}

data class Hand(val cards: List<Card>, val bid: Int) : Comparable<Hand> {

    val set = cards.toSet()

    val value: Int
        get() = if (isFiveOfAKind) {
            10
        } else if (isFourOfAKind) {
            9
        } else if (isFullHouse) {
            8
        } else if (isThreeOfAKind) {
            7
        } else if (isTwoPair) {
            6
        } else if (isOnePair) {
            5
        } else {
            0
        }

    val isFiveOfAKind: Boolean
        get() = set.size == 1

    val isFourOfAKind: Boolean
        get() = if (set.size == 2) {
            cards.count { card -> card.label == set.first().label }.let { it == 1 || it == 4}
        } else {
            false
        }

    val isFullHouse: Boolean
        get() = if (set.size == 2) {
            !isFourOfAKind
        } else {
            false
        }

    val isThreeOfAKind: Boolean
        get() = if (set.size == 3) {
            set.maxOfOrNull { distinct -> cards.count { card -> card == distinct } } == 3
        } else {
            false
        }

    val isTwoPair: Boolean
        get() = if (set.size == 3) {
            !isThreeOfAKind
        } else {
            false
        }

    val isOnePair: Boolean
        get() = set.size == 4

    override fun compareTo(other: Hand): Int {
        return if (value != other.value) {
            value.compareTo(other.value)
        } else {
            var diff = 0
            for (i in cards.indices) {
                diff = cards[i].compareTo(other.cards[i])
                if (diff != 0) {
                    break
                }
            }
            diff
        }

    }
}

fun parseHands(input: List<String>): List<Hand> {
    return input.map { line ->
        val (hand, bid) = line.split(" ")
        Hand(hand.map { c -> Card(c) }, bid.toInt())
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val hands = parseHands(input).sorted().also(::println)
        return hands.mapIndexed { index, hand ->
            (index + 1) * hand.bid
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
