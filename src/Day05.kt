fun main() {
    fun part1(input: List<String>): Long {
        data class ConverterEntry(
            val firstIndex : Long,
            val size : Long,
            val destination : Long
        )
        return input[0].substringAfter("seeds: ").split(" ").map { seed ->
            var i = seed.toLong()
            var mapValueFound = false
            input.drop(1).forEach {
                if (it.endsWith(":")) {
                    mapValueFound = false
                } else if (!mapValueFound && it.isNotEmpty()) {
                    val (destination, firstIndex, size) = it.split(" ").map { s -> s.toLong() }
                    if (i >= firstIndex && i < firstIndex + size) {
                        i = destination + (i - firstIndex)
                        mapValueFound = true
                    }
                }
            }
            i
        }.minOf { it }
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    val part1TestResult = part1(testInput)
    println("Part 1 test result is: $part1TestResult")
    println("Part 1 actual result is: ${part1(input)}")
    check(part1TestResult == 35L)

    val part2TestResult = part2(testInput)
    println("Part 2 test result is: $part2TestResult")
    println("Part 2 actual result is: ${part2(input)}")
    check(part2TestResult == 5905L)

}
