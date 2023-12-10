import java.util.LinkedList

fun main() {
    fun part1(input: List<String>): Long {
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

    /***
     * seeds: 79 14 55 13
     *
     * seed-to-soil map:
     * 50 98 2
     * 52 50 48
     *
     * ...
     */
    fun part2(input: List<String>): Long {
        data class SeedsGroup(var first: Long, var size: Long)

        val allSeeds = input[0].substringAfter("seeds: ").split(" ").map { it.toLong() }
        val seeds = ArrayList<SeedsGroup>()
        for (i in 0..<allSeeds.size / 2) {
            seeds.add(SeedsGroup(first = allSeeds[i * 2], size = allSeeds[i * 2 + 1]))
        }
        val nextSeeds = ArrayList<SeedsGroup>()
        input.drop(1).forEach {
            if (it.endsWith(":")) {
                seeds.addAll(nextSeeds)
                nextSeeds.clear()
            } else if (it.isNotEmpty()) {
                val (destination, firstIndex, size) = it.split(" ").map { s -> s.toLong() }
                val seedsIterator = seeds.listIterator()
                while (seedsIterator.hasNext()) {
                    val seedGroup = seedsIterator.next()
                    if (firstIndex <= seedGroup.first &&
                        firstIndex + size >= seedGroup.first &&
                        firstIndex + size < seedGroup.first + seedGroup.size) {
                        // first half moves
                        val newFirst = firstIndex + size
                        val newSize = seedGroup.first + seedGroup.size - newFirst
                        val nextFirst = destination + seedGroup.first - firstIndex
                        nextSeeds.add(SeedsGroup(nextFirst, seedGroup.size - newSize))
                        seedGroup.first = newFirst
                        seedGroup.size = newSize
                    } else if (firstIndex > seedGroup.first &&
                        firstIndex < seedGroup.first + seedGroup.size &&
                        firstIndex + size >= seedGroup.first + seedGroup.size) {
                        // second half moves
                        val newSize = firstIndex - seedGroup.first
                        val nextFirst = destination
                        nextSeeds.add(SeedsGroup(nextFirst, seedGroup.size - newSize))
                        seedGroup.size = newSize
                    } else if (firstIndex <= seedGroup.first && firstIndex + size >= seedGroup.first + seedGroup.size) {
                        // full move
                        val nextFirst = destination + seedGroup.first - firstIndex
                        nextSeeds.add(SeedsGroup(nextFirst, seedGroup.size))
                        seedsIterator.remove()
                    } else if (firstIndex > seedGroup.first &&
                        firstIndex < seedGroup.first + seedGroup.size - 1 &&
                        firstIndex + size < seedGroup.first + seedGroup.size) {
                        // middle part move
                        val leftNewFirst = seedGroup.first
                        val leftNewSize = firstIndex - seedGroup.first
                        val rightNewFirst = firstIndex + size
                        val rightNewSize = seedGroup.first + size - rightNewFirst
                        val nextFirst = destination
                        val nextSize = size
                        nextSeeds.add(SeedsGroup(nextFirst, nextSize))
                        seedsIterator.add(SeedsGroup(leftNewFirst, leftNewSize))
                        seedGroup.first = rightNewFirst
                        seedGroup.size = rightNewSize
                    }
                }
            }
        }
        seeds.addAll(nextSeeds)
        return seeds.minOf { it.first }
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
    check(part2TestResult == 46L)
    println("Part 2 actual result is: ${part2(input)}")

}
