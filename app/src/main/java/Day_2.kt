import Result.Companion.getResult
import java.io.FileReader

fun main(args: Array<String>) {

    val firstStrategy = FileReader("input_day_2.txt").useLines { sequence ->
        sequence.fold(mutableListOf<Int>()) { acc, t ->
            val (opponent, me) = t.split(" ")
            val myPlay = scoreMap[me]
            val opponentPlay = scoreMap[opponent]
            val score = myPlay.against(opponentPlay)
            acc.add(score)
            acc
        }
    }
    println(firstStrategy.sum())

    val actualStrategy = FileReader("input_day_2.txt").useLines { sequence ->
        sequence.fold(mutableListOf<Int>()) { acc, t ->
            val (opponent, result) = t.split(" ")
            val gameResult = getResult(result)
            val opponentPlay = scoreMap[opponent]
            val score = getMyScore(opponentPlay, gameResult)
            acc.add(score)
            acc
        }
    }
    println(actualStrategy.sum())
}

val scoreMap = mapOf(
    "A" to Hand.ROCK,
    "B" to Hand.PAPER,
    "C" to Hand.SCISSORS,
    "X" to Hand.ROCK,
    "Y" to Hand.PAPER,
    "Z" to Hand.SCISSORS
)

enum class Hand(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

fun Hand?.against(opponent: Hand?): Int {
    if (this == null || opponent == null) return 0

    return score + when (this) {
        Hand.ROCK -> {
            when (opponent) {
                Hand.ROCK -> 3
                Hand.PAPER -> 0
                Hand.SCISSORS -> 6
            }
        }
        Hand.PAPER -> {
            when (opponent) {
                Hand.ROCK -> 6
                Hand.PAPER -> 3
                Hand.SCISSORS -> 0
            }
        }
        Hand.SCISSORS -> {
            when (opponent) {
                Hand.ROCK -> 0
                Hand.PAPER -> 6
                Hand.SCISSORS -> 3
            }
        }
    }
}

enum class Result(val code: String, val score: Int) {
    LOSE("X", 0),
    DRAW("Y", 3),
    WIN("Z", 6);

    companion object {

        fun getResult(code: String): Result? = values().firstOrNull{ it.code == code }
    }
}

fun getMyScore(opponent: Hand?, result: Result?): Int {
    if (opponent == null || result == null) return 0

    return result.score + when (opponent) {
        Hand.ROCK -> {
            when (result) {
                Result.LOSE -> Hand.SCISSORS.score
                Result.DRAW -> Hand.ROCK.score
                Result.WIN -> Hand.PAPER.score
            }
        }
        Hand.PAPER -> {
            when (result) {
                Result.LOSE -> Hand.ROCK.score
                Result.DRAW -> Hand.PAPER.score
                Result.WIN -> Hand.SCISSORS.score
            }
        }
        Hand.SCISSORS -> {
            when (result) {
                Result.LOSE -> Hand.PAPER.score
                Result.DRAW -> Hand.SCISSORS.score
                Result.WIN -> Hand.ROCK.score
            }
        }
    }
}