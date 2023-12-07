import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.sqrt

fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].split(":")[1].split(" ").filterNot { it.isEmpty() }.map { it.toInt() }
        val records = input[1].split(":")[1].split(" ").filterNot { it.isEmpty() }.map { it.toInt() }
        return times.mapIndexed { i, time ->
            var successfulRaces = 0
            for (j in 1..<time) {
                if ((time - j) * j > records[i]) {
                    successfulRaces++
                }
            }
            successfulRaces
        }.reduce { a, b -> a * b }
    }

    fun part2(input: List<String>): Int {
        val time = input[0].split(":")[1].replace(" ", "").toBigDecimal()
        val record = input[1].split(":")[1].replace(" ", "").toBigDecimal()
        val discriminantSqrt = ((time * time) - (BigDecimal(4) * record)).sqrt(MathContext.DECIMAL128)
        val maxAnswer = (time + discriminantSqrt) / BigDecimal(2)
        val minAnswer = (time - discriminantSqrt) / BigDecimal(2)
        return (maxAnswer.setScale(0, RoundingMode.DOWN) - minAnswer.setScale(0, RoundingMode.UP)).toInt() + 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val part1TestResult = part1(testInput)
    println("Part 1 test result is: $part1TestResult")
    check(part1TestResult == 288)

    val part2TestResult = part2(testInput)
    println("Part 2 test result is: $part2TestResult")
    check(part1TestResult == 288)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
