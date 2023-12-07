import java.math.BigDecimal

fun main() {
    data class CardsWithScore(
        val combination: String,
        val score: Int,
        val orderScore: Long = 0,
        val bet: Int
    )

    fun String.isFiveOfAKind() = this.toCharArray().all { it == this[0] }
    fun String.isFourOfAKind() = false
    fun String.getMaxOfAKind(): Int {
        this.toCharArray().groupBy { it }
        return 0
    }

    fun String.isFullHouse(): Boolean {
        val a = this.toCharArray()
        a.sort()
        return (a[0] != a[4] &&
                a[0] == a[1] &&
                (a[1] == a[2] || a[2] == a[3]) &&
                a[3] == a[4])
    }

    fun getCombinationScore(s: String): Int {
        val cardCounts = s.groupBy { it }.map { it.value.size }.sortedDescending()
        if (cardCounts[0] == 5) {
            // 22222
            return 6
        } else if (cardCounts[0] == 4) {
            // 22221
            return 5
        } else if (cardCounts[0] == 3 && cardCounts[1] == 2) {
            // 22233
            return 4
        } else if (cardCounts[0] == 3) {
            // 222AK
            return 3
        } else if (cardCounts[0] == 2 && cardCounts[1] == 2) {
            //2233A
            return 2
        } else if (cardCounts[0] == 2) {
            // 22AKJ
            return 1
        } else {
            return 0
        }
    }

    fun getCombinationScore2(s: String): Int {
        val jokersNum = s.count { it == 'J' }
        if (jokersNum == 0) {
            return getCombinationScore(s)
        }

        val cardCounts = s.groupBy { it }.filter { it.key != 'J' }.map { it.value.size }.sortedDescending()
        if (jokersNum >= 4) {
            return 6
        }
        val mostFrequent = cardCounts[0] + jokersNum
        if (mostFrequent == 5) {
            // 22222
            return 6
        } else if (mostFrequent == 4) {
            // 22221
            return 5
        } else if (mostFrequent == 3 && cardCounts[1] == 2) {
            // 22233
            return 4
        } else if (mostFrequent == 3) {
            // 222AK
            return 3
        } else if (mostFrequent == 2) {
            // 22AKJ
            return 1
        } else {
            return 0
        }
    }

    fun getCardsOrderScore(s: String): Long {
        val cards = "23456789TJQKA"
        val allCardsNum = cards.length.toDouble()
        return s.mapIndexed { i, c ->
            Math.pow(allCardsNum, allCardsNum - i).toLong() * cards.indexOf(c)
        }.sum()
    }

    fun getCardsOrderScore2(s: String): Long {
        val cards = "J23456789TQKA"
        val allCardsNum = cards.length.toDouble()
        return s.mapIndexed { i, c ->
            Math.pow(allCardsNum, allCardsNum - i).toLong() * cards.indexOf(c)
        }.sum()
    }


    fun part1(input: List<String>): Int =
        input.map { it ->
            val elements = it.split(" ")
            elements[0] to elements[1].toInt()
            CardsWithScore(
                combination = elements[0],
                score = getCombinationScore(elements[0]),
                orderScore = getCardsOrderScore(elements[0]),
                bet = elements[1].toInt()
            )
        }.sortedWith(compareBy({ it.score }, { it.orderScore }))
            .mapIndexed { index, cardsWithScore -> (index + 1) * cardsWithScore.bet }.sum()

    fun part2(input: List<String>): Int =
        input.map { it ->
            val elements = it.split(" ")
            elements[0] to elements[1].toInt()
            CardsWithScore(
                combination = elements[0],
                score = getCombinationScore2(elements[0]),
                orderScore = getCardsOrderScore2(elements[0]),
                bet = elements[1].toInt()
            )
        }.sortedWith(compareBy({ it.score }, { it.orderScore }))
            .mapIndexed { index, cardsWithScore -> (index + 1) * cardsWithScore.bet }.sum()


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")
    val part1TestResult = part1(testInput)
    println("Part 1 test result is: $part1TestResult")
    println("Part 1 actual result is: ${part1(input)}")
    check(part1TestResult == 6440)

    val part2TestResult = part2(testInput)
    println("Part 2 test result is: $part2TestResult")
    println("Part 2 actual result is: ${part2(input)}")
    check(part2TestResult == 5905)
}
