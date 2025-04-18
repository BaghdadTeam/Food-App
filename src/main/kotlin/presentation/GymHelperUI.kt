package presentation.feature

import GymMealHelperUseCase
import org.example.presentation.Feature
import org.example.presentation.SearchFoodByDateUI

class GymHelperUI(

    private val useCase: GymMealHelperUseCase
) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {

        try {
            print(" Enter desired calories: ")
            val calories = readlnOrNull()?.toIntOrNull() ?: throw Exception("Invalid number")

            print(" Enter desired protein : ")
            val protein =
                readlnOrNull()?.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number")

            val matchingMeals = useCase.getGymMealsSuggestion(calories, protein)

            if (matchingMeals.isEmpty()) {
                println("No meals found matching your nutritional goals.")
            } else {
                println("\n Meals matching your gym goals:\n")

                val RESET = "\u001B[0m"
                val HEADER_COLOR = "\u001B[44;97m"
                val NAME_ROW_COLOR = "\u001B[47;30m"
                val VALUE_ROW_COLOR = "\u001B[106;30m"

                fun String.pad(width: Int) = this.padEnd(width, ' ')

                fun printLine() {
                    println("+${"-".repeat(5)}+${"-".repeat(25)}+${"-".repeat(12)}+${"-".repeat(12)}+")
                }

                printLine()
                println(
                    HEADER_COLOR + "| ID  | ${"Meal Name".pad(24)}| ${"Calories".pad(11)}| ${
                        "Protein(g)".pad(11)
                    }|" + RESET
                )
                printLine()

                matchingMeals.forEachIndexed { index, meal ->
                    val id = "${index + 1}".pad(4)
                    val name = meal.name?.pad(24) ?: "N/A".pad(24)
                    val cal = meal.nutrition?.calories?.toString()?.pad(11) ?: "N/A".pad(11)
                    val proteinVal = meal.nutrition?.protein?.toString()?.pad(11) ?: "N/A".pad(11)

                    val row = "| $id | $name| $cal| $proteinVal|"

                    val rowColor = if (index % 2 == 0) NAME_ROW_COLOR else VALUE_ROW_COLOR
                    println(rowColor + row + RESET)

                    printLine()
                }

            }
        } catch (e: Throwable) {
            println(" Error: ${e.message}")
        }
    }

    companion object {
        const val FEATURE_ID = 9
        const val FEATURE_NAME = "Gym helper"
    }
}



