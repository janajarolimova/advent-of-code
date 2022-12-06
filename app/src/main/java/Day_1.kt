import java.io.FileReader

fun main(args: Array<String>) {
    val fileInput = FileReader("input_day_1.txt").useLines {
            it.fold(mutableListOf(mutableListOf<Int>())) { acc, t ->
                if (t.isEmpty()) acc.add(mutableListOf())
                else acc.last().add(t.toInt())
                acc
            }
    }

    val listOfSums = fileInput.map { elf -> elf.sumOf { it } }.sortedDescending()
    
    val elfWithMostCalories = listOfSums.first()
    val topThreeElves = listOfSums.take(3).sum()

    println(elfWithMostCalories)
    println(topThreeElves)
}