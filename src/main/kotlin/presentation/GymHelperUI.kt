package presentation.feature

import GymMealHelperUseCase
import org.example.presentation.Feature
import org.example.presentation.SearchFoodByDateUI

class GymHelperUI(

    private val useCase: GymMealHelperUseCase
) : Feature {
    override val id: Int = SearchFoodByDateUI.FEATURE_ID
    override val name: String = SearchFoodByDateUI.FEATURE_NAME

    override fun execute() {

        try {
            print(" Enter desired calories: ")
            val calories =
                readlnOrNull()?.toIntOrNull() ?: throw Exception("Invalid number")

            print(" Enter desired protein (g): ")
            val protein =
                readlnOrNull()?.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number")

            val matchingMeals = useCase.getGymMealsSuggestion(calories, protein, 30)

            if (matchingMeals.isEmpty()) {
                println("No meals found matching your nutritional goals.")
            } else {
                println("Meals matching your gym goals:")
                matchingMeals.forEachIndexed { index, meal ->
                    println("${index + 1}. ${meal.name} - Calories: ${meal.nutrition?.calories}, Protein: ${meal.nutrition?.protein}g")
                }
            }

        } catch (e: Throwable) {
            println("Error: ${e.message}")
        }
    }

    companion object {
        const val FEATURE_ID = 9
        const val FEATURE_NAME = "Gym helper"
    }
}



