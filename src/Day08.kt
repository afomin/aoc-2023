fun main() {
    /***
     * LLR
     *
     * AAA = (BBB, BBB)
     * BBB = (AAA, ZZZ)
     * ZZZ = (ZZZ, ZZZ)
     */

    fun part1(input: List<String>): Long {
        val routes = input.drop(2).map {
            val (source, left, right) = it.split(Regex("[ (),=]+"))
            source to Pair(left, right)
        }.toMap()
        var curr = "AAA"
        var steps = 0L
        var pathPosition = 0
        val path = input[0]
        while (curr != "ZZZ") {
            val direction = path[pathPosition]
            if (direction == 'L') {
                curr = routes[curr]!!.first
            } else {
                curr = routes[curr]!!.second
            }
            steps++
            if (++pathPosition == path.length) {
                pathPosition = 0
            }
        }
        return steps
    }

    /***
     * LR
     *
     * 11A = (11B, XXX)
     * 11B = (XXX, 11Z)
     * 11Z = (11B, XXX)
     * 22A = (22B, XXX)
     * 22B = (22C, 22C)
     * 22C = (22Z, 22Z)
     * 22Z = (22B, 22B)
     * XXX = (XXX, XXX)
     */
    fun part2(input: List<String>): Long {
        val routes = input.drop(2).map {
            val (source, left, right) = it.split(Regex("[ (),=]+"))
            source to Pair(left, right)
        }.toMap()
        var curr = routes.keys.filter { it.endsWith('A') }
        var steps = 0L
        var pathPosition = 0
        val path = input[0]
        while (!curr.all { it.endsWith('Z') }) {
            val direction = path[pathPosition]
            curr = curr.map { if (direction == 'L') routes[it]!!.first else routes[it]!!.second}
            steps++
            if (++pathPosition == path.length) {
                pathPosition = 0
            }
            if (steps % 100000000 == 0L) {
                println("we've done $steps steps so far...")
            }
        }
        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val test2Input = readInput("Day08_test2")
    val input = readInput("Day08")
    val part1TestResult = part1(testInput)
    println("Part 1 test result is: $part1TestResult")
    println("Part 1 actual result is: ${part1(input)}")
    check(part1TestResult == 6L)

    val part2TestResult = part2(test2Input)
    println("Part 2 test result is: $part2TestResult")
    println("Part 2 actual result is: ${part2(input)}")
    check(part2TestResult == 6L)
}
