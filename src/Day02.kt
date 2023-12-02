import kotlin.math.max

fun readGames(input: List<String>): List<Pair<Int, List<Map<String, Int>>>> {
    return input.map { line ->
        val (part1, part2) = line.split(":")
        val gameId = part1.substringAfter(" ").toInt()
        val game = part2.split(";").map { draw ->
            draw.split(",").associate { single ->
                val (number, color) = single.trim().split(" ")
                color to number.toInt()
            }
        }
        gameId to game
    }
}

fun List<Map<String, Int>>.maxValues(): Triple<Int, Int, Int> {
    var red = 0; var green = 0; var blue = 0

    forEach { draw ->
        red = max(red, draw["red"] ?: 0)
        green = max(green, draw["green"] ?: 0)
        blue = max(blue, draw["blue"] ?: 0)
    }

    return Triple(red, green, blue)
}

fun main() {

    fun part1(input: List<String>): Int {
        val games = readGames(input)
        return games.sumOf { game ->
            val (red, green, blue) = game.second.maxValues()

            if (red <= 12 && green <= 13 && blue <= 14) {
                game.first
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Int {
        val games = readGames(input)
        return games.sumOf { game ->
            val (red, green, blue) = game.second.maxValues()
            red * green * blue
        }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
