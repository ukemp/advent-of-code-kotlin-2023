fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val chars = line.toCharArray()
            "${chars.first { c -> c.isDigit() }}${chars.last { c -> c.isDigit() }}".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val digits = listOf(
            "0" to 0,
            "1" to 1,
            "2" to 2,
            "3" to 3,
            "4" to 4,
            "5" to 5,
            "6" to 6,
            "7" to 7,
            "8" to 8,
            "9" to 9,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )
        return input.sumOf { line ->
            val first = digits.minBy { pair -> line.indexOf(pair.first).let { if (it == -1) Int.MAX_VALUE else it } }.second
            val last = digits.maxBy { pair -> line.lastIndexOf(pair.first) }.second
            "$first$last".toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    //check(part2(testInput) == 281)

    val input = readInput("Day01")
    check(part1(input) == 54338)
    check(part2(input) == 53389)
}
